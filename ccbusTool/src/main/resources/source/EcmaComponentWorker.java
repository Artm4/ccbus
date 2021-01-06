import packa.EcmaComp;
import packa.WorkerServer;

//import packa.*;
//import packa.packb.EcmaCompB;


public final class EcmaComponentState<T,E> extends Ext.Inner
{



    public void run()
    {

        new WorkerServer<String>()
        {
            // Server thread
            public void compute()
            {
                DbModelUser model=new DbModelUser();
                model.setAge(vals);
                for(int i=0;i<valsa.length;i++)
                {
                    model.addIncome(valsa);
                }

                DbModelBook book=new DbModelBook();
                book.save(EcmaComponent.this);

                String someStr=book.getName();

                Object book=new Object();

                ViewBook vBook=new EcmaComp(book);
                complete(someStr);
            }

            // UI thread
            public void onCompletion(String value)
            {

            }

            public void onError(Error error)
            {

            }

            public void onProgress(Progress progress)
            {

            }
        };
    }

}