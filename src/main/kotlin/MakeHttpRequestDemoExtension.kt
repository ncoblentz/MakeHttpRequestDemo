import burp.api.montoya.BurpExtension
import burp.api.montoya.MontoyaApi
import burp.api.montoya.ui.contextmenu.AuditIssueContextMenuEvent
import burp.api.montoya.ui.contextmenu.ContextMenuEvent
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider
import burp.api.montoya.ui.contextmenu.WebSocketContextMenuEvent
import java.awt.Component
import javax.swing.JMenuItem

/* Uncomment this section if you wish to use persistent settings and automatic UI Generation from: https://github.com/ncoblentz/BurpMontoyaLibrary
import com.nickcoblentz.montoya.settings.*
import de.milchreis.uibooster.model.Form
import de.milchreis.uibooster.model.FormBuilder
*/

// Montoya API Documentation: https://portswigger.github.io/burp-extensions-montoya-api/javadoc/burp/api/montoya/MontoyaApi.html
// Montoya Extension Examples: https://github.com/PortSwigger/burp-extensions-montoya-api-examples

class MakeHttpRequestDemoExtension : BurpExtension, ContextMenuItemsProvider {
    private lateinit var api: MontoyaApi


    // Uncomment this section if you wish to use persistent settings and automatic UI Generation from: https://github.com/ncoblentz/BurpMontoyaLibrary
    // Add one or more persistent settings here
    // private lateinit var exampleNameSetting : StringExtensionSetting


    private val tryHTTPVerbsMenuItem = JMenuItem("Try HTTP Verbs")
    private val httpMenuItems = listOf(tryHTTPVerbsMenuItem)


    override fun initialize(api: MontoyaApi?) {

        // In Kotlin, you have to explicitly define variables as nullable with a ? as in MontoyaApi? above
        // This is necessary because the Java Library allows null to be passed into this function
        // requireNotNull is a built-in Kotlin function to check for null and throw an Illegal Argument exception if it is null
        // after checking for null, the Kotlin compiler knows that any reference to api below will not = null and you no longer have to check it
        requireNotNull(api) { "api : MontoyaApi is not allowed to be null" }

        // Assign the MontoyaApi instance (not nullable) to a class instance variable to be accessible from other functions in this class
        this.api = api

        // This will print to Burp Suite's Extension output and can be used to debug whether the extension loaded properly
        api.logging().logToOutput("Started loading the extension...")

        /* Uncomment this section if you wish to use persistent settings and automatic UI Generation from: https://github.com/ncoblentz/BurpMontoyaLibrary

        exampleNameSetting = StringExtensionSetting(
            // pass the montoya API to the setting
            api,
            // Give the setting a name which will show up in the Swing UI Form
            "My Example Setting Name Here",
            // Key for where to save this setting in Burp's persistence store
            "MyPluginName.ExampleSettingNameHere",
            // Default value within the Swing UI Form
            "default value here",
            // Whether to save it for this specific "PROJECT" or as a global Burp "PREFERENCE"
            ExtensionSettingSaveLocation.PROJECT
            )


        // Create a list of all the settings defined above
        // Don't forget to add more settings here if you define them above
        val extensionSetting = listOf(exampleNameSetting)

        val gen = GenericExtensionSettingsFormGenerator(extensionSetting, "Jwt Token Handler")
        val settingsFormBuilder: FormBuilder = gen.getSettingsFormBuilder()
        val settingsForm: Form = settingsFormBuilder.run()

        // Tell Burp we want a right mouse click context menu for accessing the settings
        api.userInterface().registerContextMenuItemsProvider(ExtensionSettingsContextMenuProvider(api, settingsForm))

        // When we unload this extension, include a callback that closes any Swing UI forms instead of just leaving them still open
        api.extension().registerUnloadingHandler(ExtensionSettingsUnloadHandler(settingsForm))
        */

        // Name our extension when it is displayed inside of Burp Suite
        api.extension().setName("Make HTTP Request Demo")

        // Code for setting up your extension starts here...

        // Tell Burp Suite that the methods for generating right-click context menus is found here in #
        api.userInterface().registerContextMenuItemsProvider(this)



        // Code for setting up your extension ends here

        // See logging comment above
        api.logging().logToOutput("...Finished loading the extension")

    }

    // Return right-click context menu items when you are interacting with HTTP requests/responses (various tools/tabs)
    override fun provideMenuItems(event: ContextMenuEvent?): List<Component> {
        event?.let {
            if(it.selectedRequestResponses().isNotEmpty()) {
                return httpMenuItems
            }
        }
        return emptyList()
    }

    // Return right-click context menu items when you are interacting with web socket messages.
    override fun provideMenuItems(event: WebSocketContextMenuEvent?): List<Component> {
        return emptyList()
    }

    // Return right-click context menu items when you are interacting with audit issues.
    override fun provideMenuItems(event: AuditIssueContextMenuEvent?): List<Component> {
        return emptyList()
    }
}