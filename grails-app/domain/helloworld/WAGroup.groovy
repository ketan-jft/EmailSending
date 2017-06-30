package helloworld

class WAGroup {

    String groupName
    String groupAdmin
    String adminNumber
    String groupLink
    static constraints = {
        groupName(blank: false)
        groupAdmin(blank: false)
        adminNumber(blank: true)
        groupLink(blank: false,url: true)
    }
}
