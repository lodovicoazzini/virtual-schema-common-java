package com.exasol.adapter.sql;

import com.exasol.adapter.AdapterException;

import java.util.Collections;
import java.util.List;

public class SqlFunctionScalarExtract extends SqlNode {
    private static final String EXTRACT = "EXTRACT";
    private final String toExtract;
    private final List<SqlNode> arguments;

    public SqlFunctionScalarExtract(final String toExtract, final List<SqlNode> arguments) {
        SqlArgumentValidator.validateSqlFunctionArguments(arguments, SqlFunctionScalarExtract.class);
        this.arguments = arguments;
        this.toExtract = toExtract;
        for (final SqlNode node : this.arguments) {
            node.setParent(this);
        }
    }

    public String getToExtract() {
        return toExtract;
    }

    public List<SqlNode> getArguments() {
        if (arguments == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(arguments);
        }
    }

    @Override
    public String toSimpleSql() {
        assert arguments.size() == 1 && arguments.get(0) != null;
        return "EXTRACT (" + toExtract + " FROM " + arguments.get(0).toSimpleSql() + ")";
    }

    @Override
    public SqlNodeType getType() {
        return SqlNodeType.FUNCTION_SCALAR_EXTRACT;
    }

    @Override
    public <R> R accept(final SqlNodeVisitor<R> visitor) throws AdapterException {
        return visitor.visit(this);
    }

    public String getFunctionName() {
        return EXTRACT;
    }

    public ScalarFunction getFunction() {
        return ScalarFunction.EXTRACT;
    }
}