package com.exasol.adapter.sql;

import com.exasol.adapter.AdapterException;

import java.util.Collections;
import java.util.List;

public class SqlFunctionAggregateGroupConcat extends SqlNode {
    private final AggregateFunction function;
    private final boolean distinct;
    private final List<SqlNode> arguments;
    private final String separator;
    private final SqlOrderBy orderBy;

    public SqlFunctionAggregateGroupConcat(final AggregateFunction function, final List<SqlNode> arguments,
                                           final SqlOrderBy orderBy, final boolean distinct,
                                           final String separator) {
        SqlArgumentValidator.validateSqlFunctionArguments(arguments, SqlFunctionAggregateGroupConcat.class);
        this.function = function;
        this.distinct = distinct;
        this.arguments = arguments;
        this.orderBy = orderBy;
        this.separator = separator;

        for (final SqlNode node : this.arguments) {
            node.setParent(this);
        }
        if (orderBy != null) {
            orderBy.setParent(this);
        }
    }

    public AggregateFunction getFunction() {
        return function;
    }

    public List<SqlNode> getArguments() {
        if (arguments == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(arguments);
        }
    }

    public boolean hasOrderBy() {
        return orderBy != null && orderBy.getExpressions() != null && !orderBy.getExpressions().isEmpty();
    }

    public SqlOrderBy getOrderBy() {
        return orderBy;
    }

    public String getFunctionName() {
        return function.name();
    }

    public String getSeparator() {
        return separator;
    }

    public boolean hasDistinct() {
        return distinct;
    }

    @Override
    public String toSimpleSql() {
        String distinctSql = "";
        if (distinct) {
            distinctSql = "DISTINCT ";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append(getFunctionName());
        builder.append("(");
        builder.append(distinctSql);
        assert arguments != null;
        assert arguments.size() == 1 && arguments.get(0) != null;
        builder.append(arguments.get(0).toSimpleSql());
        if (orderBy != null) {
            builder.append(" ");
            builder.append(orderBy.toSimpleSql());
        }
        if (separator != null) {
            builder.append(" SEPARATOR ");
            builder.append("'");
            builder.append(separator);
            builder.append("'");
        }
        builder.append(")");
        return builder.toString();
    }

    @Override
    public SqlNodeType getType() {
        return SqlNodeType.FUNCTION_AGGREGATE;
    }

    @Override
    public <R> R accept(final SqlNodeVisitor<R> visitor) throws AdapterException {
        return visitor.visit(this);
    }
}