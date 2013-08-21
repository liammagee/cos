package edu.rmit.sustainability.server;

import edu.rmit.sustainability.data.EmpireSDB2ManagerFactory;
import edu.rmit.sustainability.model.*;
import edu.rmit.sustainability.model.ahp.*;
import org.apache.commons.beanutils.BeanUtils;
import org.openrdf.query.algebra.In;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankIssueControllerServlet extends HttpServlet {

    private final DomainExtractor domainExtractor = new DomainExtractor();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        // TODO: Needs to be externalised into configuration

        if ("overview".equals(action)) {
            rankIssues(req, resp);
        } else if ("simpleRanking".equals(action)) {
            showSimpleRanking(req, resp);
        } else if ("updateSimpleRanking".equals(action)) {
            updateSimpleRanking(req, resp);
        } else if ("criteriaRanking".equals(action)) {
            showCriteriaBasedRanking(req, resp);
        } else if ("newCriterion".equals(action)) {
            newCriterion(req, resp);
        } else if ("editCriterion".equals(action)) {
            editCriterion(req, resp);
        } else if ("updateCriterion".equals(action)) {
            updateCriterion(req, resp);
        } else if ("deleteCriterion".equals(action)) {
            deleteCriterion(req, resp);
        } else if ("updateCriteriaRanking".equals(action)) {
            updateCriteriaBasedRanking(req, resp);
        } else if ("viewCriteriaMatrix".equals(action)) {
            viewCriteriaMatrix(req, resp);
        } else if ("viewIssueMatrix".equals(action)) {
            viewIssueMatrix(req, resp);
        } else if ("rebuildAHP".equals(action)) {
            rebuildAHP(req, resp);
        } else if ("directScoring".equals(action)) {
            directScoring(req, resp);
        } else if ("ahpAnalysis".equals(action)) {
            ahpAnalysis(req, resp);
        } else if ("updateCriteriaMatrix".equals(action)) {
            updateCriteriaMatrix(req, resp);
        } else if ("updateIssueMatrix".equals(action)) {
            updateIssueMatrix(req, resp);
        }

    }


    private void rankIssues(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        Map<Integer, List<CriticalIssue>> rankings = project.getSimpleCriticalIssueRanking();
        req.setAttribute("rankedIssues", rankings);
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/rank_issues.jsp").forward(req, resp);
    }


    private void showSimpleRanking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        Map<Integer, List<CriticalIssue>> rankings = project.getSimpleCriticalIssueRanking();
        req.setAttribute("rankedIssues", rankings);
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/simple_ranking.jsp").forward(req, resp);
    }

    private void updateSimpleRanking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        try {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            em.refresh(project);
            for (CriticalIssue criticalIssue : project.getCriticalIssues()) {
                String significance = req.getParameter(criticalIssue.getId());
                if (significance != null) {
                    try {
                        criticalIssue.setPerceivedSignificance(Integer.parseInt(significance));
                    } catch (NumberFormatException e) {
                    }
                }
            }
            em.merge(project);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Map<Integer, List<CriticalIssue>> rankings = project.getSimpleCriticalIssueRanking();
        req.setAttribute("rankedIssues", rankings);
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/simple_ranking.jsp").forward(req, resp);
    }


    private void showCriteriaBasedRanking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        if (project.getAhp() == null || project.getAhp().getCriteria() == null || project.getAhp().getCriteria().size() == 0) {
            try {
                EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

                project.initialiseAHP();
                em.merge(project);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        viewCriteriaBasedRanking(req, resp);
    }

    private void newCriterion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        Criterion criterion = new Criterion();
        criterion.setName("New Criterion");
        criterion.setDescription("");
        criterion.setInitialWeight(1);
        criterion.setCurrentProject(project);
        criterion.setCurrentUser(user);
        req.getSession().setAttribute("criterion", criterion);
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/edit_criterion.jsp").forward(req, resp);
    }

    private void editCriterion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        String criterionID = req.getParameter("criterionID");
        if (criterionID != null) {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            Criterion criterion = em.find(Criterion.class, URI.create(criterionID));
            if (criterion != null) {
                criterion.setCurrentProject(project);
                criterion.setCurrentUser(user);
                req.getSession().setAttribute("criterion", criterion);
            }

        }
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/edit_criterion.jsp").forward(req, resp);
    }


    private void updateCriterion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = (Project) req.getSession().getAttribute("project");
        Criterion criterion = (Criterion) req.getSession().getAttribute("criterion");

        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            Map<String, String[]> parameters = new HashMap<String, String[]>(req.getParameterMap());
            BeanUtils.populate(criterion, parameters);


            // Use 'merge' when updating an object
            if (criterion.getRdfId() == null) {
                List<Criterion> criteria = project.getAhp().getCriteria();
                criteria.add(criterion);
                project.setAhp(new AHP(criteria, project.getCriticalIssues()));
                // Save the project - issues should save as well
                em.merge(project);
            } else {
                em.merge(criterion);
            }
            em.refresh(project);

        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        viewCriteriaBasedRanking(req, resp);
    }

    private void deleteCriterion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        String criterionID = req.getParameter("criterionID");
        if (criterionID != null) {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            Criterion criterion = em.find(Criterion.class, URI.create(criterionID));
            em.remove(criterion);
            List<Criterion> criteria = project.getAhp().getCriteria();
            criteria.remove(criterion);
            project.setAhp(new AHP(criteria, project.getCriticalIssues()));
            em.merge(project);
        }
        viewCriteriaBasedRanking(req, resp);
    }

    private void viewCriteriaBasedRanking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
//        project.getAhp().rebuildMatrices();
        Map<Integer, List<CriticalIssue>> rankings = project.getCriteriaBasedCriticalIssueRanking();
        req.setAttribute("rankedIssues", rankings);
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/criteria_based_ranking.jsp").forward(req, resp);
    }

    private void rebuildAHP(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        try {
            // Persist...
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);

            project.setAhp(new AHP(project.getAhp().getCriteria(), project.getCriticalIssues()));
            em.merge(project);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        viewCriteriaBasedRanking(req, resp);
    }

    private void viewCriteriaMatrix(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/view_criteria_matrix.jsp").forward(req, resp);
    }

    private void viewIssueMatrix(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/view_issue_matrix.jsp").forward(req, resp);
    }


    private void directScoring(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        Project project = (Project) req.getSession().getAttribute("project");
        Map<Integer, List<CriticalIssue>> rankings = project.getCriteriaBasedCriticalIssueRanking();
        req.setAttribute("rankedIssues", rankings);
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/criteria_direct_scoring.jsp").forward(req, resp);
    }

    private void ahpAnalysis(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        Project project = (Project) req.getSession().getAttribute("project");
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/ahp_analysis.jsp").forward(req, resp);
    }


    private void updateCriteriaMatrix(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        CriteriaMatrix criteriaMatrix = project.getAhp().getCriteriaMatrix();
        for (MatrixRow matrixRow : criteriaMatrix.getMatrixRows()) {
            for (MatrixCell matrixCell : matrixRow.getCells()) {
                String cellID = matrixCell.getId();
                String cellValue = req.getParameter(cellID);
                try {
                    double cellValueAsDouble = Double.parseDouble(cellValue);
                    matrixCell.setValue(cellValueAsDouble);
                } catch (NumberFormatException e) {
                }
            }
        }
        try {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            em.merge(project);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/view_criteria_matrix.jsp").forward(req, resp);
    }

    private void updateIssueMatrix(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");
        String criterionID = req.getParameter("criterionID");
        IssueMatrix issueMatrix = project.getAhp().getIssueMatrix(criterionID);
        for (MatrixRow matrixRow : issueMatrix.getMatrixRows()) {
            for (MatrixCell matrixCell : matrixRow.getCells()) {
                String cellID = matrixCell.getId();
                String cellValue = req.getParameter(cellID);
                try {
                    double cellValueAsDouble = Double.parseDouble(cellValue);
                    matrixCell.setValue(cellValueAsDouble);
                } catch (NumberFormatException e) {
                }
            }
        }
        try {
            EntityManager em = EmpireSDB2ManagerFactory.createEmpireEntityManager(UserControllerServlet.empirePropertyPath);
            em.merge(project);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/view_issue_matrix.jsp").forward(req, resp);
    }


    private void updateCriteriaBasedRanking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = (Project) req.getSession().getAttribute("project");

        Map<Integer, List<CriticalIssue>> rankings = project.getCriteriaBasedCriticalIssueRanking();
        req.setAttribute("rankedIssues", rankings);
        getServletContext().getRequestDispatcher("/fragments/issue/ranking/criteria_based_ranking.jsp").forward(req, resp);
    }


}
