package edu.rmit.sustainability.data;
/***********************************************************************
 * Module:  DBDataStorage.java
 * Author:  Guillaume Prevost
 * Purpose: Defines the Class DBDataStorage
 ***********************************************************************/

import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DBDataStorage {

    /**
     * Database connection credentials, as constants.
     */
    protected static final String DB_CONNECTION_NAME = "jdbc:postgresql:mydb";
    protected static final String DB_CONNECTION_USER = "postgres";
    protected static final String DB_CONNECTION_PASS = "1234";


    /**
     * Method authenticateUser: establish a connection to the PostgreSQL database
     *
     * @return boolean: whether connection to the database has been established or not
     * @author: Lida Ghahremanloo
     */
    public Connection getConnection() {
        Connection sql_connection = null;

        // Load the driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
            e.printStackTrace();
            return null;
        }

        try {
            sql_connection = DriverManager.getConnection(DB_CONNECTION_NAME, DB_CONNECTION_USER, DB_CONNECTION_PASS);
            if (sql_connection != null)
                return sql_connection;
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method storeProject: create or update a project in the database
     *
     * @param Project project: the project to add or update
     * @param User    user: the user requesting the creation or the update
     * @return boolean: whether the operation succeeded or failed
     * @author: Lida Ghahremanloo
     */
    public boolean storeProject(Project project, User user) {
        if (project != null && user != null) {
            Connection con = getConnection();
            if (con != null) {
                try {
                    String insertProject = "INSERT INTO projects (projectid, name) VALUES (?, ?)";
                    String insertRelation = "INSERT INTO relations (userid, projectid) VALUES (?, ?)";

                    PreparedStatement insertProjectStatement = con.prepareStatement(insertProject);

                    insertProjectStatement.setString(1, project.getId());
                    insertProjectStatement.setString(2, project.getProjectName());
                    int result = insertProjectStatement.executeUpdate();
                    insertProjectStatement.close();

                    if (result != 0) {
                        PreparedStatement insertRelationStatement = con.prepareStatement(insertRelation);
                        insertRelationStatement.setString(1, user.getUsername());
                        insertRelationStatement.setString(2, project.getId());
                        result = insertRelationStatement.executeUpdate();
                        insertRelationStatement.close();
                    }

                    return (result != 0);
                } catch (SQLException e) {
                    System.out.println("Failed to add project!");
                    e.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException sqle) {
                        System.out.println("Failed to close connection!");
                        sqle.printStackTrace();
                    }
                }
            }
        }
        return (false);
    }

    /**
     * Method updateProject: update a project in the database
     *
     * @param Project project: the project to add or update
     * @param User    user: the user requesting the creation or the update
     * @return boolean: whether the operation succeeded or failed
     * @author: Lida Ghahremanloo
     */
    public boolean updateProject(Project project, User user) {
        if (project != null && user != null) {
            Connection con = getConnection();
            if (con != null) {
                try {
                    String updateProject = "UPDATE projects set NAME = ? WHERE projectid = ?";

                    PreparedStatement updateProjectStatement = con.prepareStatement(updateProject);

                    updateProjectStatement.setString(1, project.getProjectName());
                    updateProjectStatement.setString(2, project.getId());
                    int result = updateProjectStatement.executeUpdate();
                    updateProjectStatement.close();

                    System.out.println("res: " + result);

                    return (result != 0);
                } catch (SQLException e) {
                    System.out.println("Failed to add project!");
                    e.printStackTrace();
                } finally {
                    try {
                        con.close();
                    } catch (SQLException sqle) {
                        System.out.println("Failed to close connection!");
                        sqle.printStackTrace();
                    }
                }
            }
        }
        return (false);
    }

    /**
     * Method retrieveProjects: retrieve a user's projects
     *
     * @param User user: the user who wants to retrieve his project
     * @return ArrayList<Project>: the list of project related to this user
     * @author: Guillaume Prevost
     */
    public ArrayList<Project> retrieveProjects(User user) {
        Connection con = getConnection();
        if (con != null) {
            try {
                String query = "SELECT * FROM projects WHERE projectid IN (SELECT projectid FROM relations WHERE userid = '" + user.getUsername() + "');";

                Statement sql_statement = con.createStatement();
                ResultSet sql_result = sql_statement.executeQuery(query);

                ArrayList<Project> projectList = new ArrayList<Project>();
                while (sql_result.next())
                    projectList.add(new Project(sql_result.getString(2)));
                return (projectList);
            } catch (SQLException e) {
                System.out.println("Failed to retrieve user's projects.");
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    System.out.println("Failed to close connection!");
                    sqle.printStackTrace();
                }
            }
        }
        return (null);
    }

    /**
     * Method authenticateUser: test whether a user exists or not in the database
     *
     * @param User user: the user to authenticate
     * @return boolean: whether the user/password couple exists in the database or not
     * @author: Lida Ghahremanloo
     */
    public boolean authenticateUser(User user) {
        Connection con = getConnection();
        if (con != null) {
            try {
                /*
                     * Testing if sql queries are correct 			 *
                     * String query="SELECT password FROM users WHERE userid="+"'Lida';";
                     * */

                String query = "SELECT password FROM users WHERE userid = '" + user.getUsername() + "';";
                Statement sql_statement = con.createStatement();
                ResultSet sql_result = sql_statement.executeQuery(query);
                String dbpassword = null;
                while (sql_result.next())
                    dbpassword = sql_result.getString(1);

                if (dbpassword != null && dbpassword.equals(user.getPassword())) {
                    System.out.println("The user is authorized");
                    sql_statement.close();
                    con.close();
                    return (true);
                }
            } catch (SQLException e) {
                System.out.println("Failed to authenticate user!");
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    System.out.println("Failed to close connection!");
                    sqle.printStackTrace();
                }
            }
        }
        return (false);
    }

    /**
     * Method deleteProject: delete a project from the database
     *
     * @param Project project: the project to delete
     * @return boolean: whether the operation succeeded or failed
     * @author: Guillaume Prevost
     */
    public boolean deleteProject(Project project) {
        Connection con = getConnection();
        if (con != null && project != null) {
            String query1 = "DELETE FROM relations WHERE projectid = '" + project.getId() + "';";
            String query2 = "DELETE FROM projects WHERE projectid = '" + project.getId() + "';";

            try {
                Statement sql_statement1 = con.createStatement();
                int sql_result1 = sql_statement1.executeUpdate(query1);
                Statement sql_statement2 = con.createStatement();
                int sql_result2 = sql_statement2.executeUpdate(query2);

                if (sql_result1 != 0 && sql_result2 != 0)
                    System.out.println("Project " + project.getId() + " successfully deleted !");
                return (true);
            } catch (SQLException e) {
                System.out.println("Failed to delete the project " + project.getId() + " !");
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    System.out.println("Failed to close connection!");
                    sqle.printStackTrace();
                }
            }
        }
        return (false);
    }

    /**
     * Method projectExists: test whether a project exists or not
     *
     * @param Project project: the project to check
     * @return boolean: whether the project exists or not in the database
     * @author: Guillaume Prevost
     */
    public boolean projectExists(String projectid) {
        Connection con = getConnection();
        if (con != null) {
            try {
                String query = "SELECT name FROM projects WHERE projectid = '" + projectid + "';";
                Statement sql_statement = con.createStatement();
                ResultSet sql_result = sql_statement.executeQuery(query);
                if (sql_result.next())
                    return (true);
                else
                    return (false);
            } catch (SQLException e) {
                System.out.println("");
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    System.out.println("Failed to close connection!");
                    sqle.printStackTrace();
                }
            }
        }
        return (false);
    }

    /**
     * Method projectExists: test whether a project exists or not
     *
     * @param String projectID: the ID of the project to check
     * @return boolean: whether the project exists or not in the database
     * @author: Guillaume Prevost
     */
    public boolean projectExists(Project project) {
        if (project != null)
            return (this.projectExists(project.getId()));
        return (false);
    }

    /**
     * Method projectBelongsTo: whether the given user belongs to the given project or not
     *
     * @param Project project: the project to check
     * @param User    user: the user to check
     * @return boolean: whether the given user belongs to the given project or not
     * @author: Guillaume Prevost
     */
    public boolean projectBelongsTo(String projectID, String userID) {
        Connection con = getConnection();
        if (con != null) {
            try {
                String query = "SELECT projectID FROM relations WHERE projectid = '" + projectID + "' AND userid = '" + userID + "';";
                Statement sql_statement = con.createStatement();
                ResultSet sql_result = sql_statement.executeQuery(query);
                if (sql_result.next())
                    return (true);
                else
                    return (false);
            } catch (SQLException e) {
                System.out.println("");
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    System.out.println("Failed to close connection!");
                    sqle.printStackTrace();
                }
            }
        }
        return (false);
    }

    /**
     * Method projectBelongsTo: whether the given user belongs to the given project or not
     *
     * @param String projectID: the ID of the project to check
     * @param String userID: the ID of the user to check
     * @return boolean: whether the given user belongs to the given project or not
     * @author: Guillaume Prevost
     */
    public boolean projectBelongsTo(Project project, User user) {
        if (project != null && user != null)
            return (this.projectBelongsTo(project.getId(), user.getUsername()));
        return (false);
    }

    /**
     * Method authenticateUserAndRetrieveProjects: test whether a user exists or not in the database, and retrieve his/her projects
     *
     * @param User user: the user to log in
     * @return ArrayList<Project>: if the user is authenticated, all the project in which the user is involved in, null otherwise
     * @author: Guillaume Prevost
     */
    public ArrayList<Project> authenticateUserAndRetrieveProjects(User user) {
        // If the user/password couple exists in the database
        if (user != null && this.authenticateUser(user))
            return (retrieveProjects(user));
        return (null);
    }


    /**
     * Method getProjectByID: whether the given user belongs to the given project or not
     *
     * @param Project project: the project to check
     * @param User    user: the user to check
     * @return boolean: whether the given user belongs to the given project or not
     * @author: Liam Magee
     */
    public Project getProjectByName(String projectName) {
        Project project = null;
        Connection con = getConnection();
        if (con != null) {
            try {
                String query = "SELECT name FROM projects WHERE projectid = ?";
                PreparedStatement sql_statement = con.prepareStatement(query);
                sql_statement.setString(1, projectName);
                ResultSet sql_result = sql_statement.executeQuery();
                if (sql_result.next())
                    project = new Project(sql_result.getString(1));
            } catch (SQLException e) {
                System.out.println("");
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    System.out.println("Failed to close connection!");
                    sqle.printStackTrace();
                }
            }
        }
        return project;
    }
}

