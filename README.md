# HPE ALM Octane GoCD plugin ![Build State on Travis-CI](https://travis-ci.org/Haufe-Lexware/octane-gocd-plugin.svg?branch=master)
This plugin integrates GoCD with HPE ALM Octane. Making GoCD pipelines accessible in HPE ALM Octane and allowing to
analyze build quality and test results.

## Requirements
This plugin requires GoCD to run in version 17.9 or higher.

## How to install
This plugin has to be installed on your GoCD-server. Take the following steps:
1. Download the jar-file of this plugin and store it on your go-server in `<go-server-directory>/plugins/external/`.
2. Restart your GoCD-server.
3. You now need to configure the plugin to tell against which HPE ALM Octane server it should connect:
    1. In GoCD open `Admin` and `Plugins`: you should see the *OctaneGoCDPlugin*. Click the cogwheel in front of it.
    2. Set the *Server URL* of your Octane server. This URL is supposed to look like
       `http://hostname:port/ui/?p=<SharedSpaceID>`.
    3. Set the *Client ID* and *Client Secret*. These are the credentials on your Octane server the plugin should use.
    4. Set the *Go API Username* and *Go API Password*. These are the credentials the plugin uses to access the Go server.
    5. When you try to save your settings, the connection to Octane is tested. Also the given credentials to the Go server
	   API are given a try. If both work your settings will be saved, else validation warning will be shown.
4. Now you should be able to add your GoCD server in Octane as a *CI Server*:
    1. In Octane click the cogwheel to enter the admin settings for your spaces.
    2. Open the tab `DevOps` and open the section `CI Servers`.
    3. Click the plus to add a new CI Server.
    4. You should be able to see your GoCD server in the dropdown list, select it and give it a speaking name. Save it.
    5. Back in the overview of *CI Servers* you should now see your Go server, with its *name*, *instance id*, *server type*,
       *URL*, *connection status* and *SDK version*.

When everything is set up correctly you can start adding pipelines in Octane.

## Features of this plugin
This GoCD plugin exposes pipelines towards Octane.
 * Whenever a pipeline starts building, Octane will be informed. Octane will show in the *Pipelines Live Summary* that
   the pipeline is building. It will also show the estimated build duration which was calculated by the plugin.
 * Whenever a pipeline finishes building, Octane will be informed. The plugin will provide the build duration, build
   stability, test results and SCM changes to Octane. Octane will analyze the given information and show it in various
   views.
 * From within Octane any pipeline can be triggered to run.

### Test results
In order to see test results of your pipelines in Octane. The plugin has to translate the test reports into Octane's
data model. Therefor *xml-report-parsers* for *JUnit* and *NUnit* are integrated into this plugin. To allow the
plugin to parse your xml-report-files, you have to declare them as artifacts of build. Whenever a pipeline finishes the
plugin will search all artifacts, and it will try to parse all xml files assuming they are the result-file of
JUnit or NUnit. If successful the contained test results are transmitted to Octane.

### Triggering a pipeline to run
In Octane the feature *Run Pipeline* might be deactivated. The reason is that ALM Octane only activates it for *known*
CI Server types, to which *GoCD* does currently not belong to. As long as in ALM Octane in the overview `CI Servers`
your GoCD server is displayed as type `unknown`, the feature *Run Pipeline* will not get active.

## Restrictions of this plugin
This plugin allows your GoCD server to connect to exactly one Shared Space on exactly one ALM Octane instance. It is
not possible to connect your GoCD server instance to multiple shared spaces or multiple ALM Octane instances.

## Bugs and Feature Requests
The transmission of test results to Octane, especially the mapping into Octane's domain model is arbitrary. Please file
an issue if you feel something is wrong with it. Also, if your project uses other test tools than JUnit or NUnit, then
please create a feature request and provide an example of your report file(s) allong with it. Technically every test tool
can be used, as long as it generates some sort of report-file.
