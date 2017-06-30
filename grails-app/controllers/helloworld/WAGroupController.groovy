package helloworld

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class WAGroupController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond WAGroup.list(params), model:[WAGroupCount: WAGroup.count()]
    }

    def show(WAGroup WAGroup) {
        respond WAGroup
    }

    def create() {
        respond new WAGroup(params)
    }

    @Transactional
    def save(WAGroup WAGroup) {
        if (WAGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (WAGroup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond WAGroup.errors, view:'create'
            return
        }

        WAGroup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'WAGroup.label', default: 'WAGroup'), WAGroup.id])
                redirect WAGroup
            }
            '*' { respond WAGroup, [status: CREATED] }
        }
    }

    def edit(WAGroup WAGroup) {
        respond WAGroup
    }

    @Transactional
    def update(WAGroup WAGroup) {
        if (WAGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (WAGroup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond WAGroup.errors, view:'edit'
            return
        }

        WAGroup.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'WAGroup.label', default: 'WAGroup'), WAGroup.id])
                redirect WAGroup
            }
            '*'{ respond WAGroup, [status: OK] }
        }
    }

    @Transactional
    def delete(WAGroup WAGroup) {

        if (WAGroup == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        WAGroup.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'WAGroup.label', default: 'WAGroup'), WAGroup.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'WAGroup.label', default: 'WAGroup'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
