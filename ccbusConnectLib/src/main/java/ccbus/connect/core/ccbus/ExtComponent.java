package ccbus.connect.core.ccbus;

import ccbus.connect.core.react.Component;
import ccbus.connect.ecma.Document;
import ccbus.connect.ecma.Node;
import ccbus.connect.system.ExportModule;

import java.util.function.BiFunction;

@ExportModule
public class ExtComponent extends Component
{
    public class Ref
    {
        public Node current;
    }


    public void stateKey(String key, Object value){}
    public void stateKeySet(Object ...value){}

    public void onChangeProp(String propKey,BiFunction<Object,Object,Object> propFuncion){}

    public ExtComponent(){}
    public ExtComponent(Object props){}

    protected Ref createRef(){return new Ref();}

    public Document getDocument()
    {
        return new Document();
    }

    public void renderRe(){}
}