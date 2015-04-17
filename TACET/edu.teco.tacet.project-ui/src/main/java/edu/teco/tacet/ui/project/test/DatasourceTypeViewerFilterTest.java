package edu.teco.tacet.ui.project.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ViewerFilter;
import org.junit.Test;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.ui.project.filters.DatasourceTypeViewerFilter;
import edu.teco.tacet.ui.project.filters.Mode;

public class DatasourceTypeViewerFilterTest {

    interface DummyDatasource extends Datasource {}

    @Test
    public void datasourceTest() {
        ViewerFilter filterKeep =
            new DatasourceTypeViewerFilter<>(DummyDatasource.class, Mode.KEEP);
        ViewerFilter filterRemove =
            new DatasourceTypeViewerFilter<>(DummyDatasource.class, Mode.REMOVE);
        
        Project project = MetaFactory.eINSTANCE.createProject();
        Datasource ds = new DummyDatasource() {
            
            @Override
            public void eSetDeliver(boolean deliver) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void eNotify(Notification notification) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public boolean eDeliver() {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public EList<Adapter> eAdapters() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public void eUnset(EStructuralFeature feature) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void eSet(EStructuralFeature feature, Object newValue) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public Resource eResource() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public boolean eIsSet(EStructuralFeature feature) {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean eIsProxy() {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public Object eInvoke(EOperation operation, EList<?> arguments)
                throws InvocationTargetException {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Object eGet(EStructuralFeature feature, boolean resolve) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Object eGet(EStructuralFeature feature) {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public EList<EObject> eCrossReferences() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public EList<EObject> eContents() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public EReference eContainmentFeature() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public EStructuralFeature eContainingFeature() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public EObject eContainer() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public EClass eClass() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public TreeIterator<EObject> eAllContents() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public void setName(String value) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void setMetadata(Map<Object, Object> value) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void setIsInMemory(boolean value) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void setId(long value) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public boolean isIsInMemory() {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public EList<Timeseries> getTimeseries() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String getName() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Map<Object, Object> getMetadata() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public long getId() {
                // TODO Auto-generated method stub
                return 0;
            }
        };
        
        assertTrue(filterKeep.select(null, project, ds));
        assertFalse(filterRemove.select(null, project, ds));
        assertFalse(filterKeep.select(null, project, MetaFactory.eINSTANCE.createDatasource()));
    }

}
