package ccbus.out;

import java.util.Date;
public class AngularComponent
{
    void onClick()
    {
        String some;
        int a=1;
        Runnable run=new Runnable()
        {
            public int out;
            void run()
            {
                out=1;
            }
        }
        ((Runnable) run).out

    }
}


//package view;
//
//import connect.*;
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
//            out=Predicate.query(filter);
//        };
//        User userList = worker.run();
//        datasource.fromList(userList);
//        for (Obj i : items)
//        {
//            datasource.add(i);
//        }
//    }
//}