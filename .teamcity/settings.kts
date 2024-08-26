import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.03"

project {
    description = "manual test project"

    vcsRoot(TeamcityTest)
    vcsRoot(HttpsGithubComFarkasmateTeamcityTestGitRefsHeadsMaster)

    buildType(manual_1)
    buildType(FromUrl)
}

object FromUrl : BuildType({
    name = "from_url"

    vcs {
        root(HttpsGithubComFarkasmateTeamcityTestGitRefsHeadsMaster)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object manual_1 : BuildType({
    name = "manual_1"

    vcs {
        root(TeamcityTest)
    }

    steps {
        script {
            name = "echo_test"
            id = "echo_test"
            scriptContent = """echo "Hello World!""""
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComFarkasmateTeamcityTestGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/farkasmate/teamcity-test.git#refs/heads/master"
    url = "https://github.com/farkasmate/teamcity-test.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
    }
    param("teamcitySshKey", "teamcity")
})

object TeamcityTest : GitVcsRoot({
    name = "teamcity-test"
    url = "https://github.com/farkasmate/teamcity-test.git"
    branch = "master"
    branchSpec = "ci"
})
