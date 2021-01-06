package ccbus.system;

import ccbus.system.query.*;
import ccbus.system.query.select.SelectBase;

import ccbus.system.test.Point;
import ccbus.system.test.Shape;


import javax.persistence.*;
import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.List;
import java.util.TimeZone;

class DateId
{
    java.util.Date date;
    Long id;

    public DateId(java.util.Date date, Long id)
    {
        this.date = date;
        this.id = id;
    }
}

public class Main {

    @PersistenceUnit
    EntityManagerFactory emf;

    public void test()
    {
        TimeZone.setDefault( TimeZone.getTimeZone( "UTC" ) );


        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        // Check database version
        String sql = "select version()";

        String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
        System.out.println(result);

        Shape shape=new Shape();
        //entityManager.persist(shape);

        Point point=new Point(1,2);
        point.setShape(shape);

        // init Select
        SelectBase.setEntityManager(entityManager);
        SelectStackEntity<Point> select=new SelectStackEntity<>(Point.class);



//        try {
//            Field f=Point.class.getDeclaredField("x");
//            Class c=f.getClass();
//            System.out.println(c.isPrimitive());
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        Field[] fieldResult = Point.class.getDeclaredFields();
//        int a=fieldResult.length;


        //Select<> select=Select.create(Point.class);


//

//        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//        CriteriaQuery<Shape> q = cb.createQuery(Shape.class);
//        Root<Shape> c = q.from(Shape.class);
//
//       ParameterExpression<Date> pp = cb.parameter(Date.class);
//        q.select(c).where(cb.equal(c.get("date"),pp));
//
//        TypedQuery<Shape> typedQuery = entityManager.createQuery(q).setParameter(pp,Date.valueOf("2019-10-16"));
//         Shape p=typedQuery.getResultList().get(0);

// array

//        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
//        Root<Shape> c = q.from(Shape.class);
//
//        ParameterExpression<Date> pp = cb.parameter(Date.class);
//        q.select(cb.array(c.get("date"),c.get("id"))).where(cb.equal(c.get("date"),pp));
//
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(q).setParameter(pp,Date.valueOf("2019-10-16"));
//        List<Object[]> results=typedQuery.getResultList();
//        for (Object[] r : results) {
//            System.out.println(r);
//        }



//
//        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object> q = cb.createQuery(Object.class);
//        Root<Shape> c = q.from(Shape.class);
//
//       ParameterExpression<Date> pp = cb.parameter(Date.class);
//        q.select(c).where(cb.equal(c.get("date"),pp));
//
//        TypedQuery<Object> typedQuery = entityManager.createQuery(q).setParameter(pp,Date.valueOf("2019-10-16"));
//        Shape p=(Shape)typedQuery.getResultList().get(0);
//        System.out.println(p);


//        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
//        Root<Shape> c = q.from(Shape.class);
//
//        ParameterExpression<Date> pp = cb.parameter(Date.class);
//        q.multiselect(c.get("date"),c.get("id")).where(cb.lessThan(c.get("date"),pp));
//
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(q).setParameter(pp,Date.valueOf("2019-10-16"));
//        List<Object[]> p=typedQuery.getResultList();
//        System.out.println(p);

//        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
//        Root<Shape> c = q.from(Shape.class);
//
//        ParameterExpression<Date> pp = cb.parameter(Date.class);
//        q.multiselect(c.get("date"),c.get("id")).where(cb.lessThan(c.get("date"),pp));
//
//        TypedQuery<Tuple> typedQuery = entityManager.createQuery(q).setParameter(pp,Date.valueOf("2019-10-16"));
//        List<Tuple> p=typedQuery.getResultList();
//        for (Tuple item:p)
//        {
//            System.out.println(item.get(0));
//        }



        // construct
//        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//        CriteriaQuery<DateId> q = cb.createQuery(DateId.class);
//        Root<Shape> c = q.from(Shape.class);
//
//        ParameterExpression<Date> pp = cb.parameter(Date.class);
//        q.select(cb.construct(DateId.class,c.get("date"),c.get("id")))
//                .where(cb.lessThan(c.get("date"),pp));
//
//        TypedQuery<DateId> typedQuery = entityManager.createQuery(q).setParameter(pp,Date.valueOf("2019-10-16"));
//        List<DateId> p=typedQuery.getResultList();
//        System.out.println(p.get(0).date);


//                CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
//        Root<Point> c = q.from(Point.class);
//
//        ParameterExpression<Long> pp = cb.parameter(Long.class);
//        ParameterExpression<Long> ppA = cb.parameter(Long.class);
//        Join<Point,Shape> joinShape=c.join("shape");
//
//        q.multiselect(joinShape.get("date"),joinShape.join("shapeType").get("name"),
//                cb.concat(cb.concat(c.get("id"),"some"),"other"),
//                c.join("shape",JoinType.LEFT).get("date"),
//                c.get("x"),
//                cb.max(joinShape.get("id")));
//
//
//        CriteriaBuilder.In<Long> inClause = cb.in(c.get("id"));
//        inClause.value(pp);
//        inClause.value(ppA);
//
//        q.where(inClause);
//
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(q).setParameter(pp,12L).setParameter(ppA,13L);
//        List<Object[]> p=typedQuery.getResultList();
//        System.out.println(p);


//        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
//        Root<ShapeType> c = q.from(ShapeType.class);
//
//        ParameterExpression<Long> pp = cb.parameter(Long.class);
//        ParameterExpression<Long> ppA = cb.parameter(Long.class);
//        Join joinShape=c.join("shape");
//
//        HashMap<String,Join> hashJoin=new HashMap<>();
//        hashJoin.put("shape",c.join("shape"));
//        hashJoin.put("point",hashJoin.get("shape").join("point"));
//
//        q.multiselect(hashJoin.get("shape").get("date"),hashJoin.get("point").get("x"),
//                cb.concat(cb.concat(c.get("name"),"some"),"other"),
//                c.join("shape",JoinType.LEFT).get("date"),
//                cb.max(joinShape.get("id")));
//
//        CriteriaBuilder.In<Long> inClause = cb.in(c.get("id"));
//        inClause.value(pp);
//        inClause.value(ppA);
//
//        q.where(inClause);
//
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(q)
//                .setFirstResult(10) // offset
//                .setMaxResults(20) // limit
//                .setParameter(pp,12L).setParameter(ppA,13L);
//        List<Object[]> p=typedQuery.getResultList();
//        System.out.println(p);


//
//
//        System.out.println(p.getDate().toString());

        //ParameterExpression<Integer> a = cb.parameter(Integer.class);
//
//        Expression<Integer> x = c.get("x");
//
//        cb.greaterThan(x, pp);
//        q.where(
//                cb.and(
//                        cb.or(cb.gt(x, a),cb.gt(x, pp)),
//                        cb.or(cb.gt(x, a),cb.gt(x, pp))
//                )
//
//
//
//        );
//


//        select.where(
//            select.whereOr(
//                    select.whereGt(),
//                    select.whereLt()),
//            select.whereAnd(
//                    select.whereEq(),
//                    select.whereLt(),
//            )
//
//        )
//
//        select.whereAnd().whereEq(Meta.point.shape.id,1)
//                .whereOr().whereGt().whereLt().whereClose()
//                .whereAnd().whereEq().whereLt()

        //     TypedQuery<Point> typedQuery = entityManager.createQuery(q).setParameter(pp,0).setParameter(a,0);
        //     Point p=typedQuery.getResultList().get(0);

        //  select.join(PointMeta.shape).whereAnd().whereGt(PointMeta.shape.name,13);
        // select.sum(PointMeta.shape.name).col(PointMeta.shape.name,PointMeta.shape.name,PointMeta.shape.name)
        //   select.whereAnd().whereGt("x",0);
        //    Point p=select.resultEntitySingle();
        //   System.out.println(p.getX()+","+p.getY()+" shape"+p.getShape().getId());
        //entityManager.persist(point);

//        Date date=Date.valueOf("2019-10-16");
//
//        select.whereAnd().whereEq(Meta.point.shape.id,12L).whereEq(Meta.point.shape.date,date);


//      select.whereAnd()
//              .whereOr().whereLt(Meta.point.y,100).whereGt(Meta.point.x,0).whereClose()
//              .whereOr().whereLt(Meta.point.y,100).whereGt(Meta.point.x,0).whereClose();
        //     Point p=select.resultEntitySingle();
//      System.out.println(p.getX()+","+p.getY()+" shape"+p.getShape().getDate());
//        Date date = null;
//
        //    SelectStack<Shape> selectShape=new SelectStack(Shape.class);
//        try
//        {
//             date = new SimpleDateFormat("yyyy-MM-dd").parse("2019-10-16");
//        } catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
//        selectShape.whereAnd().whereEq(Meta.shape.date,Date.valueOf("2019-10-16"));
//        Shape s=selectShape.resultEntitySingle();
//        System.out.println(" date:"+Date.valueOf("2019-10-16"));


//        Query q1=entityManager.createQuery(" select\n" +
//                "        shape0_.id as id1_1_,\n" +
//                "        shape0_.date as date2_1_ \n" +
//                "    from\n" +
//                "        Shape shape0_ \n" +
//                "    where\n" +
//                "        shape0_.date='2019-10-16'");
//        Object s1=q1.getResultList().get(0);
//        System.out.println(" shape"+s1);

        entityManager.getTransaction().commit();
        entityManager.close();

        JPAUtil.shutdown();
    }

    public static void main(String[] args)
    {
        new Main().test();
    }
}
