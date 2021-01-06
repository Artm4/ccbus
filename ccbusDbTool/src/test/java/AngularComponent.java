public class AngularComponent<T> extends RunWorker implements Clickable
{
    @Input
    public String someVal;
    private int result;
    void onClick()
    {
        String some;
        int a=1;
        RunWorker run=new RunWorker()
        {
            @Override
            public void run()
            {
                set(2);
            }
        };
        result=run.get();
    }

    public void run()
    {

    }
}

abstract class RunWorker
{
    private int out=1;
    public abstract void run();
    public int get(){return out;}
    public void set(int result){out=result;}
}

@interface Input
{
}

interface Clickable
{

}


//package view;
//
//import ccbus.dbtool.*;
//
//public class AngularComponent
//{
//    @Input
//    private ArralyList<String> name;
//
//    @Output
//    ArralyList<String> nameChange;
//
//    @Input
//    private String phone;
//    private Filter filter;
//    private DataSouce datasource;
//
//    void onClick()
//    {
//        Worker worker = new Worker(user)
//        {
//            out=new User();
//        };
//        User user = worker.run();
//        name = user.name;
//        phone = user.phone;
//    }
//
//    void onPageChanged()
//    {
//        Worker worker = new Worker(filter)
//        {
//            out=Predicate.result.query(filter);
//        };
//        User userList = worker.run();
//        datasource.fromList(userList);
//        for (Obj i : items)
//        {
//            datasource.add(i);
//        }
//    }
//}