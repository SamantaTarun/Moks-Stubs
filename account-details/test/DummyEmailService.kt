import models.EmailBody
import service.EmailService

class DummyEmailService : EmailService {
    var lastEmailBody : EmailBody?= null
    override fun send(emailBody: EmailBody?) {
        lastEmailBody = emailBody
    }
    fun getLastEmail() : EmailBody?{
        return this.lastEmailBody
    }

}
