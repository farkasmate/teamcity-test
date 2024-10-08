import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCompose
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

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
    description = "test project"

    vcsRoot(teamcityTest)

    buildType(build)
}

object build : BuildType({
    name = "build"

    vcs {
        root(teamcityTest)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

    steps {
        script {
            name = "echo"
            id = "echo"
            scriptContent = """
                echo "Hello World!"
            """.trimIndent()
        }

        dockerCompose {
            name = "docker_compose"
            file = "compose.yaml"
        }

        dockerCommand {
            name = "docker_run"

            commandType = other {
                subCommand = "run"
                commandArgs = "--rm matefarkas/fortune:latest"
            }
        }
    }
})

object teamcityTest : GitVcsRoot({
    name = "https://github.com/farkasmate/teamcity-test.git"
    url = "https://github.com/farkasmate/teamcity-test.git"
    branch = "master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "teamcity_test_ro"
    }
})
