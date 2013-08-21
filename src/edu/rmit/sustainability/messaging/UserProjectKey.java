package edu.rmit.sustainability.messaging;

import edu.rmit.sustainability.model.Project;
import edu.rmit.sustainability.model.User;


public class UserProjectKey {

    private int hashCode = 0;

    public static UserProjectKey makeKey(User user, Project project) {
        return new UserProjectKey(user, project);
    }

    public UserProjectKey(User user, Project project) {
        if (user != null && user.getUsername() != null && project != null && project.getProjectName() != null)
            this.hashCode = user.getUsername().hashCode() ^ project.getProjectName().hashCode();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public boolean equals(Object o) {
        return (o != null && o.getClass().equals(this.getClass()) && o.hashCode() == hashCode());
    }

}
