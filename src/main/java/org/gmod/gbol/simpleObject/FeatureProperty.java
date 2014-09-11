package org.gmod.gbol.simpleObject;

/*
 * Autogenerated extension of org.gmod.gbol.simpleObject.generated.FeatureProperty.
 * Add custom code to this file. 
 *
 * Author: Robert Bruggner, rbruggner@berkeleybop.org
 *
*/

public class FeatureProperty extends org.gmod.gbol.simpleObject.generated.AbstractFeatureProperty {

    private static final long serialVersionUID = 1L;

    /** Constructor.
     * 
     */
    public FeatureProperty(){
        super();
    }
    
    /** Copy constructor.
     * 
     */
    public FeatureProperty(FeatureProperty src) {
        this(src.getType(), src.getFeature(), src.getRank());
        setValue(src.getValue());
    }
    
    /** Alternate constructor.
     * 
     * @param type - CVTerm for the property type
     * @param feature - Feature that this property is attached to
     * @param rank - Rank of his property
     */
    public FeatureProperty(CVTerm type, Feature feature, int rank) {
        super(type, feature, rank);
    }

    /** Alternate constructor.
     * 
     * @param type - CVTerm for the property type
     * @param feature - Feature that this property is attached to
     * @param value - Value of this property
     */
    public FeatureProperty(CVTerm type, Feature feature, String value) {
        super();
        setType(type);
        setFeature(feature);
        setValue(value);
    }

    @Override
    public String toString() {
        return getType() + ":" + getValue();
    }

    @Override
    public boolean equals(Object other) {
        if(other==null || !(other instanceof FeatureProperty)) return false ;
        FeatureProperty fp = (FeatureProperty) other ;

        if (!fp.getType().equals(getType())) return false ;
        if (!fp.getValue().equals(getValue())) return false ;
        if (!fp.getFeature().equals(getFeature())) return false ;

//        System.out.println("feature: " + getFeature().toString() + " vs otther: "+ fp.getFeature().toString());

        return true ;
//        return super.equals(other);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (getType() == null ? 0 : this.getType().hashCode());
        result = 37 * result + (getValue() == null ? 0 : this.getValue().hashCode());
        result = 37 * result + (getFeature() == null ? 0 : this.getFeature().hashCode());
        return result;
    }

    //    @Override
//    public Collection<AbstractSimpleObject> getWriteObjects() {
//        ArrayList<AbstractSimpleObject> writeObjects = new ArrayList<AbstractSimpleObject>();
//        // Have to write yourself
//        writeObjects.add(this);
//        
//        // Singletons
//        writeObjects.addAll(this.getType().getWriteObjects());
//        
//        // Multiples
//        for (FeaturePropertyPublication fpp : this.getFeaturePropertyPublications())
//            writeObjects.addAll(fpp.getWriteObjects());
//        
//        return writeObjects;
//    }

    public AbstractSimpleObjectIterator getWriteableObjects()
    {
        return new SimpleObjectIterator(this);
    }
    
    private static class SimpleObjectIterator extends AbstractSimpleObjectIterator
    {
        private static class Status extends AbstractSimpleObjectIterator.Status
        {
            public static final int type = 1;
            public static final int featurePropPubs = 2;
        }
        
        public SimpleObjectIterator(FeatureProperty featureProp)
        {
            super(featureProp);
        }

        public AbstractSimpleObject next()
        {
            FeatureProperty featureProp = (FeatureProperty)object;
            AbstractSimpleObject retVal = null;
            if (status == Status.self) {
                retVal = peek();
                processSingletonIterator(Status.type, featureProp.getType());
            }
            else {
                retVal = soIter.next();
                if (status == Status.type) {
                    processCollectionIterators(Status.featurePropPubs, featureProp.getFeaturePropertyPublications());
                }
                if (status == Status.featurePropPubs) {
                    processLastCollectionIterator();
                }
            }
            current = retVal;
            return retVal;
        }
    }

}
