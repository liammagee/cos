/**
 * 
 */
package edu.rmit.sustainability.util;

import edu.rmit.sustainability.model.Project;

/**
 * @author Ke Sun
 *
 */
public class ProjectProgressUtil {
	
	public static boolean isGIDefined(Project p)
	{
		boolean result = false;
		
		if (p.getGeneralIssue() != null)
		{
			if (p.getGeneralIssue().length() != 0 && p.getGeneralIssue().compareTo("") != 0)
			{
				 result = true;
			}
		}
		
		return result;
	}
	
	public static boolean isNGDefined(Project p)
	{
		boolean result = false;
		
		if (p.getNormativeGoal() != null)
		{
			if (p.getNormativeGoal().length() != 0 && p.getNormativeGoal().compareTo("") != 0)
			{
				 result = true;
			}
		}
		
		return result;
	}
	
	public static boolean isSufficentIssues(Project p, int num)
	{
		boolean result = false;
		
		if (p.getCriticalIssues().size() >= num)
		{
			result = true;
		}
		
		return result;
	}

    public static boolean hasAssessment(Project p) {
        boolean result = false;

        if (p.getAssessments().size() >= 0) {
            result = true;
        }

        return result;
    }
}
