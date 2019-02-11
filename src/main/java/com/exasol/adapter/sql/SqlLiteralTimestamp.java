package com.exasol.adapter.sql;

import com.exasol.adapter.AdapterException;

public class SqlLiteralTimestamp extends SqlNode {
    private final String value;   // stored as YYYY-MM-DD HH:MI:SS.FF6
    
    public SqlLiteralTimestamp(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toSimpleSql() {
        return "TIMESTAMP '" + value + "'";
    }

    @Override
    public SqlNodeType getType() {
        return SqlNodeType.LITERAL_TIMESTAMP;
    }

    @Override
    public <R> R accept(final SqlNodeVisitor<R> visitor) throws AdapterException {
        return visitor.visit(this);
    }
}