/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
