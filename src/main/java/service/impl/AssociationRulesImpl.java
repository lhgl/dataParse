package service.impl;

import weka.associations.Apriori;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.io.BufferedReader;
import java.io.FileReader;

public class AssociationRulesImpl {
    public String process() {

        InstanceQuery query = null;
        try {
            query = new InstanceQuery();

            query.setUsername("root");
            query.setPassword("admin");
            query.setDatabaseURL("jdbc:mysql://localhost:3306/captura?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            query.setQuery("select * from video_action_units ;");
            // You can declare that your data set is sparse
            // query.setSparseData(true);
            Instances data = new Instances(new BufferedReader(new FileReader("data/supermarket.arff")));
                    //query.retrieveInstances();
            data.setClassIndex(data.numAttributes() - 1);

            final NumericToNominal filter = new NumericToNominal();

            filter.setInputFormat(data);
            data = Filter.useFilter(data, filter);
            if (data.size() > 0) {
                // build associator
                Apriori apriori = new Apriori();
                apriori.setClassIndex(data.classIndex());
                apriori.buildAssociations(data);
                return String.valueOf(apriori);
            } else {
                return "Not enough data provided";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
