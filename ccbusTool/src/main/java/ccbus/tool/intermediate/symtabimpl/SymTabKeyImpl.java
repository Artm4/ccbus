package ccbus.tool.intermediate.symtabimpl;

import ccbus.tool.intermediate.SymTabKey;

public enum SymTabKeyImpl implements SymTabKey
{
    PRIMITIVE_TYPE, REFERENCE_TYPE, TYPE_NAME,
    ARRAY_DIM, METHOD,
    PARSED_VAR_TYPE, PARSED_VAR_DECLARATOR_ID
}
