/*
    SGR ALPHA - QUERY PACKAGE
    File: QUERYBUILDER.JAVA | Last Major Update: 30.04.2015
    Developer: Kevin Raian, Washington Reis
    IDINALOG REBORN © 2015
*/

package sgr.sql;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    // Cria lista de consultas e sua estrutura
    List<Query> queries = new ArrayList<Query>();
    List<String> querySize = new ArrayList<String>();

    // Adiciona consulta à lista de consultas
    public void addQuery(Query query) {
        queries.add(query);
    }

    // Calcula extensão da consulta
    public String buildQuery() {
        String query = " ";
        for (int i = 0; i < querySize.size(); i++) {
            query = query + querySize.get(i);
        }
        return query;

    }
    
    // Adiciona consulta manualmente 
    public void addManualQuery(String query) {
        querySize.add(query);
    }
    
    // Adiciona consulta completa
    public void addQuery(QueryOperation operation, String field, QueryGender gender, String value, QueryType type) {
        
        // Constrói nova consulta
        Query query = new Query();
        
        // Define valores da consulta
        query.setField(field);
        query.setValue(value);
        query.setGender(gender);
        query.setType(type);
        query.setOperation(operation);
        // Adiciona consulta à lista de consultas
        queries.add(query);

        // Verifica e configura tipo de operação da consulta
        String buildOperation = "";
        if (operation.equals(operation.and)) {
            buildOperation = buildOperation + " and ";
        } else if (operation.equals(operation.or)) {
            buildOperation = buildOperation + " or ";
        } else if (operation.equals(operation.empty)) {
            buildOperation = buildOperation + " where ";
        }

        // Verifica e configura tipo de dados da consulta
        String buildType = "";
        if (type.equals(QueryType.text)) {
            buildOperation = buildOperation + "upper(" + field + ")";
            buildType = "'" + value.toUpperCase() + "'";
        } else if (type.equals(QueryType.number)) {
            buildOperation = buildOperation + field;
            buildType = value;
        } else if (type.equals(QueryType.date)) {
            buildOperation = buildOperation + "trunc(" + field + ")";
            buildType = "trunc(to_date('" + value + "','dd/mm/yyyy)'))";
        }

        // Verifica e configura gênero e condição da consulta
        if (gender.equals(gender.equal)) {
            buildOperation = buildOperation + " = " + buildType;
        } else if (gender.equals(gender.bigger)) {
            buildOperation = buildOperation + " > " + buildType;
        } else if (gender.equals(gender.smaller)) {
            buildOperation = buildOperation + " < " + buildType;
        } else if (gender.equals(gender.has)) {
            buildOperation = buildOperation + " like '%" + buildType.replaceAll("'", "").replaceAll(" ","%") + "%'";
        } else if (gender.equals(gender.different)) {
            buildOperation = buildOperation + " <> " + buildType;
        } else if (gender.equals(gender.isNull)) {
            buildOperation = buildOperation + " is null ";
        } else if (gender.equals(gender.isNotNull)) {
            buildOperation = buildOperation + " is not null ";
        } else if (gender.equals(gender.biggerEqual)) {
            buildOperation = buildOperation + " >= " + buildType;
        } else if (gender.equals(gender.smallerEqual)) {
            buildOperation = buildOperation + " <= " + buildType;
        }
        
        // Constrói operação por completo e adiciona à estrutura principal
        querySize.add(buildOperation);
    }

    // <editor-fold desc="GET and SET" defaultstate="collapsed">
    public List<Query> queries() {
        return queries;
    }

    public List<Query> getConditions() {
        return queries;
    }

    public void setConditions(List<Query> queries) {
        this.queries = queries;
    }

    public List<String> getConditionSize() {
        return querySize;
    }

    public void setCs(List<String> cs) {
        this.querySize = querySize;
    }
    // </editor-fold>
    
}
