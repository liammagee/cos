package edu.rmit.sustainability.data.query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.Indicator;
import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

public class GetIndicatorsByKeywordsQuery {
	
	public GetIndicatorsByKeywordsQuery() {
	}
	
	public List<Indicator> doQuery(Project project, User user, List<String> keywords) {
		
        List<Indicator> indicators = new ArrayList<Indicator>();
        GetIndicatorsByKeywordQuery query = new GetIndicatorsByKeywordQuery();
        for (String keyword : keywords) {
        	indicators.addAll(query.doQuery(project, user, keyword));
        }
        return(indicators);
	}
	
}
