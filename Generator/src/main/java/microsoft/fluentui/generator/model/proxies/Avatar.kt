package microsoft.fluentui.generator.model.proxies



class Avatar() : AppearanceProxy() {

    override val stylesheetName = "AvatarTokens"
    override val viewName = "AvatarTokensView"

    override fun setupCustomAttributes(): List<CustomAttribute> {
        return mutableListOf()
    }
}