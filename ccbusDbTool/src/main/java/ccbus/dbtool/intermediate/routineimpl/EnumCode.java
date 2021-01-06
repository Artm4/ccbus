package ccbus.dbtool.intermediate.routineimpl;

import ccbus.dbtool.intermediate.*;
import ccbus.dbtool.intermediate.icodeimpl.ICodeNodeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumCode extends RoutineCode {
    private Map<String, EnumConstantCode > constantHashed=new HashMap<>();
    private List<EnumConstantCode> constants=new ArrayList<>();

    public EnumCode(ICodeNodeClass nodeClass)
    {
        super(nodeClass);
    }

    public List<EnumConstantCode> getConstants() {
        return constants;
    }

    public Map<String, EnumConstantCode> getConstantsHashed() {
        return constantHashed;
    }

    public void addConstant(EnumConstantCode constantCode)
    {
        constants.add(constantCode);
        constantHashed.put(constantCode.getConstantName(),constantCode);
    }
}
