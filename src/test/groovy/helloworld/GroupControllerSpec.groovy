package helloworld

import grails.test.mixin.*
import spock.lang.*

@TestFor(GroupController)
@Mock(Group)
class GroupControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.groupList
            model.groupCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.group!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def group = new Group()
            group.validate()
            controller.save(group)

        then:"The create view is rendered again with the correct model"
            model.group!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            group = new Group(params)

            controller.save(group)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/group/show/1'
            controller.flash.message != null
            Group.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def group = new Group(params)
            controller.show(group)

        then:"A model is populated containing the domain instance"
            model.group == group
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def group = new Group(params)
            controller.edit(group)

        then:"A model is populated containing the domain instance"
            model.group == group
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/group/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def group = new Group()
            group.validate()
            controller.update(group)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.group == group

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            group = new Group(params).save(flush: true)
            controller.update(group)

        then:"A redirect is issued to the show action"
            group != null
            response.redirectedUrl == "/group/show/$group.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/group/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def group = new Group(params).save(flush: true)

        then:"It exists"
            Group.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(group)

        then:"The instance is deleted"
            Group.count() == 0
            response.redirectedUrl == '/group/index'
            flash.message != null
    }
}
