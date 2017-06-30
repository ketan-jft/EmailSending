package helloworld
import org.springframework.web.multipart.MultipartFile

class EmailSenderController {
    def mailService

    def send() {
        try {
            def multipartFile = request.getFile('attachment')
            mailService.sendMail {
                multipart true
                to params.address
                subject params.subject
                html params.body
                if(multipartFile && !multipartFile.empty) {
                    File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + multipartFile.getOriginalFilename());
                    multipartFile.transferTo(tmpFile);
                    attach tmpFile
                }
            }
            flash.message = "Message sent at " + new Date()
            redirect action: "index"
        }
        catch (Exception e){flash.message = "Error is : " + e}
    }
    def index(){}
}
