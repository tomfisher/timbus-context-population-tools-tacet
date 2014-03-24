/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
 */

package squirrel.model;

import squirrel.model.io.DataColumn;
import squirrel.util.Range;
import weka.core.AbstractInstance;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class InstancesAdapter extends Instances {

    private ModelFacade modelFacade;
    private int numAttributes;
    private int classIdx;
    private int adaptedClassIdx = -1;

    public InstancesAdapter(ModelFacade modelFacade, int classIdx) {
        super("Classify", modelFacade.getCurrentDataSource().getAttributes(), 0);
        this.modelFacade = modelFacade;
        this.numAttributes = modelFacade.getCurrentDataSource().getAttributes().size();
        this.classIdx = classIdx;
        // TODO Auto-generated constructor stub
    }

    public void trainingsInstace() {
        long offset =
            (long) ((((modelFacade.getEndTimeStamp() - modelFacade.getStartTimeStamp()) / modelFacade
                .getAverageDistance()) / 100) * modelFacade.getAverageDistance());

        long start = modelFacade.getStartTimeStamp();
        long end = start + offset;
        for (int i = 0; i < 100; i++) {
            Range range = new Range(start, end);
            synchronized (this) {
                Iterable<SensorDatum> sd =
                    modelFacade.getSensorDataInRange(range);
                int countIdx = 0;
                String value;
                for (SensorDatum sensorDatum : sd) {
                    Instance inst = new DenseInstance(numAttributes);
                    inst.setDataset(this);
                    boolean add = false;
                    int idx = 0;
                    for (int k = 0; k < modelFacade.getCurrentDataSource().getNumberOfColumns(); k++) {
                        if (modelFacade.getCurrentDataSource().getDataColumn(k).isSelected()) {
                            if (modelFacade.getCurrentDataSource().getDataColumn(k).getType()
                                .isTrainAnnotation()) {
                                if (modelFacade.getAnnotation(sensorDatum.getTimestamp(),
                                    modelFacade.getCurrentDataSource().toAnnotationIndex(k)) != null) {
                                    add = true;
                                    inst.setValue(idx, 0);
                                } else {
                                    break;
                                }
                            } else {
                                if (adaptedClassIdx == -1 && classIdx == k) {
                                    adaptedClassIdx = countIdx;
                                    this.setClassIndex(adaptedClassIdx);
                                }
                                if (adaptedClassIdx == -1)
                                    countIdx++;

                                if (modelFacade.getCurrentDataSource().getDataColumn(k).getType()
                                    .isSensor()) {
                                    double d =
                                        sensorDatum.getValues()[modelFacade
                                            .getCurrentDataSource().toSensorIndex(k)];
                                    inst.setValue(idx, d);
                                } else if (modelFacade.getCurrentDataSource().getDataColumn(k)
                                    .getType().isAnnotation()) {
                                    if (modelFacade
                                        .getAnnotation(sensorDatum.getTimestamp(), modelFacade
                                            .getCurrentDataSource().toAnnotationIndex(k)) == null) {
                                        inst.setValue(idx, 0);
                                    } else {
                                        value =
                                            modelFacade.getAnnotation(
                                                sensorDatum.getTimestamp(),
                                                modelFacade.getCurrentDataSource()
                                                    .toAnnotationIndex(k)).toValue();
                                        inst.setValue(idx, this.attribute(idx).indexOfValue(value));
                                    }
                                } else if (modelFacade.getCurrentDataSource().getDataColumn(k)
                                    .getType().isTimeStamp()) {
                                    inst.setValue(idx, sensorDatum.getTimestamp());
                                }
                            }
                            idx++;
                        }

                    }
                    if (add)
                        this.add(inst);
                }
            }

            start = end;
            if (i == 98) {
                end = modelFacade.getEndTimeStamp();
            } else {
                end += offset;
            }
        }

    }

    public void instanceToClassify(Range range) {
        Iterable<SensorDatum> sd =
            modelFacade.getSensorDataInRange(range);
        String value;
        for (SensorDatum sensorDatum : sd) {
            Instance inst = new DenseInstance(numAttributes);
            inst.setDataset(this);
            boolean add = false;
            int idx = 0;
            for (int k = 0; k < modelFacade.getCurrentDataSource().getNumberOfColumns(); k++) {
                DataColumn dataColumnK = modelFacade.getCurrentDataSource().getDataColumn(k);
                if (dataColumnK.isSelected()) {
                    if (dataColumnK.getType().isTrainAnnotation()) {
                        if (modelFacade.getAnnotation(sensorDatum.getTimestamp(),
                            modelFacade.getCurrentDataSource().toAnnotationIndex(k)) == null) {
                            add = true;
                            inst.setValue(idx, 0);
                        } else {
                            break;
                        }
                    } else {
                        if (dataColumnK.getType().isSensor()) {
                            double d =
                                sensorDatum.getValues()[modelFacade.getCurrentDataSource()
                                    .toSensorIndex(k)];
                            inst.setValue(idx, d);
                        } else if (dataColumnK.getType().isAnnotation()) {

                            if (modelFacade.getAnnotation(sensorDatum.getTimestamp(), modelFacade
                                .getCurrentDataSource().toAnnotationIndex(k)) == null) {
                                if (idx == classIdx) {
                                    inst.setValue(idx, this.attribute(idx).indexOfValue("?"));
                                } else {
                                    inst.setValue(idx, 0);
                                }
                            } else {
                                value =
                                    modelFacade
                                        .getAnnotation(
                                            sensorDatum.getTimestamp(),
                                            modelFacade.getCurrentDataSource()
                                                .toAnnotationIndex(k)).toValue();
                                inst.setValue(idx, this.attribute(idx).indexOfValue(value));
                            }
                        }
                        else if (dataColumnK.getType()
                            .isTimeStamp()) {
                            inst.setValue(idx, sensorDatum.getTimestamp());
                        }
                        idx++;
                    }
                }
                if (add)
                    this.add(inst);
            }
        }
        this.setClassIndex(classIdx);
    }

    public DataColumn.Type getOrigClassType() {
        return modelFacade.getCurrentDataSource().getDataColumn(classIdx).getType();
    }

    public int getOrigAnnotIdx() {
        return modelFacade.getCurrentDataSource().toAnnotationIndex(classIdx);
    }
}
