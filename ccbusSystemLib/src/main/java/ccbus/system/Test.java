package ccbus.system;

import ccbus.system.meta.Meta;
import ccbus.system.query.*;
import ccbus.system.query.select.SelectBase;
import ccbus.system.query.util.CriteriaWhereCallback;
import ccbus.system.test.Point;
import ccbus.system.test.Shape;
import ccbus.system.test.ShapeType;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
//import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

class DateIdC
{
    public java.util.Date date;
    public Long id;
    public Integer cnt;

    public DateIdC(java.util.Date date, Long id)
    {
        this.date = date;
        this.id = id;
    }

    public DateIdC(java.util.Date date, Long id,Integer cnt)
    {
        this.date = date;
        this.id = id;
        this.cnt=cnt;
    }
}

interface Other
{
    static final String name="other";
}

interface OtherB<T>
{
    static final String name="other";
}

abstract class Some
{
    static final String name="aome";

    public static volatile Other other;
    public static volatile OtherB<Other> my;
    public static volatile SingularAttribute<Shape, Long> id;
}

public class Test {

    void foo(Other a)
    {
        String x=a.name;
        x=x+a.name;
    }

    public static void main(String[] args)
    {
        (new Test()).foo(Some.other);
        TimeZone.setDefault( TimeZone.getTimeZone( "UTC" ) );

        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        // init Select
        SelectBase.setEntityManager(entityManager);
//
//        SelectBase.setCriteriaWhereCallback(new CriteriaWhereCallback()
//        {
//            @Override
//            public <E> Predicate onWhere(CriteriaBuilder criteriaBuilder,Root<E> root) {
//                return criteriaBuilder.equal(root.get("id"),1L);
//            }
//        });
//
//        SelectStackEntity<Shape> selectShape=new SelectStackEntity(Shape.class);
//        selectShape.whereAnd().cmpEqual(Meta.Shape.ShapeType.id,1L);
//        Shape s=selectShape.resultSingle();
//        System.out.println(" date:"+s.getDate());
//
//        List<Shape> list=selectShape.resultList();
//        System.out.println(" list:"+list);



        // Array
//        SelectStackArray<Shape> selectShapeA=new SelectStackArray(Shape.class);
//
//        selectShapeA
//                .select(
//            Meta.Shape.date,Meta.Shape.id)
//            .fCount(Meta.Shape.id)
//            .fSum(Meta.Shape.id)
//            .fCurrentDate()
//            .whereAnd().cmpIn(Meta.Shape.id, Arrays.asList(12L,14L,15L))
//                .cmpLessThan(21L).par(Meta.Shape.id)
//                .cmpLessThan(22L).par(Meta.Shape.id)
//            .whereOr()
//                .cmpLessThan(20L).par(Meta.Shape.id)
//                .cmpLessThan(21L).par(Meta.Shape.id)
//            .whereClose()
//                .cmpLessThan(22L).par(Meta.Shape.id)
//                .par(Meta.Shape.id).cmpLessThan(Meta.Shape.id,20L)
//            .group(Meta.Shape.id)
//            .havingOr()
//                .cmpLessThan(20L).fCount(Meta.Shape.id)
//                .cmpLessThan(Meta.Shape.id,20L);
//
//        selectShapeA.addWhere()
//            .whereAnd()
//                .cmpLessThan(21L).par(Meta.Shape.id)
//                .cmpLessThan(22L).par(Meta.Shape.id)
//            .whereOr()
//                .par(Meta.Shape.id) // will be ignored no compare expression on top
//                .cmpLessThan(20L).par(Meta.Shape.id).cmpLessThan(Meta.Shape.id,10L)
//            .whereClose()
//                .cmpLessThan(100L).par(Meta.Shape.id);
//
//
//
//        Object[] obj=selectShapeA.resultSingle();
//
//        System.out.println(" id:"+obj[0]);
//        System.out.println(" date:"+obj[1]);
//        System.out.println(" cnt:"+obj[2]);
//        System.out.println(" sum:"+obj[3]);
//        System.out.println(" cur date:"+obj[4]);

// Value
        SelectStackObject<Shape> selectShapeAObject=new SelectStackObject(Shape.class);
        selectShapeAObject.joinLeft(Meta.Shape.Point);
        selectShapeAObject
                .select(Meta.Shape.id,Meta.Shape.Point.id,Meta.Shape.Point.OldPoint.id)
                //.fCount(Meta.Shape.id)
                //.fSum(Meta.Shape.id)
                //.fCurrentDate()
                .whereAnd().cmpIn(Meta.Shape.id, Arrays.asList(12L,14L,15L));

        Object objV=selectShapeAObject.resultSingle();


//        System.out.println(ObjectSizeCalculator.getObjectSize(Meta.Point));
        System.out.println(" id:"+objV);

//        SelectStackArray<Point> selectPointA=new SelectStackArray(Point.class);
//        selectPointA.joinLeft(Meta.Shape);
//
//        selectPointA.offset(1).select(Meta.Point.id,Meta.Point.Shape.date,Meta.Point.Shape.ShapeType.name)
//                .whereAnd().cmpGreaterThan(Meta.Shape.id,0L);
//
//
//
//        Object[] objp=selectPointA.resultSingle();
//        System.out.println("id point: "+objp[0]);
//        System.out.println("date shape: "+objp[1]);
//        System.out.println("shape type name: "+objp[2]);


//        SelectStackArray<Shape> selectShapeAA=new SelectStackArray(Shape.class);
//
//        selectShapeAA.joinLeft(Meta.Shape.Point);
//
//        selectShapeAA.select(Meta.Shape.id,Meta.Shape.date,Meta.Shape.Point.x,Meta.Shape.Point.Point.x)
//                .whereAnd().cmpGreaterThan(Meta.Shape.id,0L).orderDesc(Meta.Shape.id).orderDesc(Meta.Shape.date);
//
//        Object[] objpA=selectShapeAA.resultSingle();
//        System.out.println("id point: "+objpA[0]);
//
//
//        SelectStackArray<ShapeType> selectShapeType=new SelectStackArray(ShapeType.class);
//        selectShapeType.select(Meta.ShapeType.id,Meta.ShapeType.shapeTypeStatus);
//        Object[] objsh=selectShapeType.resultSingle();
//        System.out.println("id shapeType: "+objsh[1].toString());
//
//        System.out.println("date shape: "+objp[1]);
//        System.out.println("shape type name: "+objp[2]);
//
//
//        // Tuple
//        SelectStackTuple<Shape> selectShapeT=new SelectStackTuple(Shape.class);
//        selectShapeT.select(Meta.Shape.date,Meta.Shape.id).whereAnd().cmpEqual(Meta.Shape.id,12L);
//
//        Tuple tuple=selectShapeT.resultSingle();
//
//        System.out.println(" id:"+tuple.get(0));
//        System.out.println(" date:"+tuple.get(1));
//
//
//        // Ctor
//
//        SelectStackCtor<Shape,DateIdC> selectShapeCtor=new SelectStackCtor(Shape.class,DateIdC.class);
//        selectShapeCtor.select(Meta.Shape.date,Meta.Shape.id).whereAnd().cmpGreaterThan(Meta.Shape.id,12L);
//        DateIdC dic=selectShapeCtor.resultSingle();
//
//        System.out.println(" id:"+dic.id);
//        System.out.println(" date:"+dic.date);
//
//        List<DateIdC> listA=selectShapeCtor.resultList();
//        System.out.println(" date:"+listA.get(1).date);

        entityManager.getTransaction().commit();
        entityManager.close();

        JPAUtil.shutdown();
    }
}
