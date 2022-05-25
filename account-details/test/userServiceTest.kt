//import dummyUserService.DummyUserService
import exceptions.NotFoundException
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import models.EmailBody
import models.User
import repository.UserRepository
import service.EmailService
import service.UserService


class UserServiceTest : StringSpec() {
    private val mockEmailService = mockk<EmailService>(relaxed = true)
    private val stubUserRepository = mockk<UserRepository>()

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        super.afterEach(testCase, result)
        clearAllMocks()
    }
    init {
        "send the mail to respected mailer"{
            val dummyEmailService = mockk<EmailService>(relaxed=true)
            val userService =  UserService(null, dummyEmailService)
            userService.sendWelcomeEmail("tarun.samanta@medly.com")
            val expectedEmailBody = EmailBody("Welcome","Welcome to the portal","tarun.samanta@medly.com")

            verify(exactly=1){
                dummyEmailService.send(expectedEmailBody)
            }
           // val actualEmailBody  = dummyEmailService.getLastEmail()
            //actualEmailBody shouldBe expectedEmailBody
        }

        "Should send user account derails"{

            val dummyEmailService = mockk<EmailService>(relaxed=true)
            val dummyRepo=mockk<DummyUserRepository>(relaxed=true)

            every{

                dummyRepo.findByEmail("tarun.samanta@medly.com")
            } returns  User("7872742607","tarun.samanta@medly.com","tarun samanta")


            val userService =  UserService(dummyRepo, dummyEmailService)
            userService.sendRegisteredPhoneNumber("tarun.samanta@medly.com")

            val expectedEmailBody=EmailBody("Account Details","Here is your Registered Phone Number: 7872742607","tarun.samanta@medly.com")

            verify(exactly=1){
                dummyEmailService.send(expectedEmailBody)
            }
            //val actualemailbody=dummyService.getLastEmail()

            //expectedmailbody shouldBe  actualemailbody


        }

        "Should send account not found details"{
            val dummyEmailService = mockk<EmailService>(relaxed=true)
            val dummyRepo=mockk<DummyUserRepository>()
            every{
                dummyRepo.findByEmail("tarun.samanta@medly.com")
            }.throws(NotFoundException())

            val userService =  UserService(dummyRepo, dummyEmailService)
            userService.sendRegisteredPhoneNumber("tarun.samaa@medly.com")

            val expectedmailbody=EmailBody("Account Details","Here is your Registered Phone Number: 7872742607","tarun.samata@medly.com")

            verify(exactly=1){

                dummyEmailService.send(expectedmailbody)
            }
            clearAllMocks();

        }
    }
}
