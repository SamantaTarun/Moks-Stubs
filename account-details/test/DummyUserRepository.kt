import exceptions.NotFoundException
import models.User
import repository.UserRepository

class DummyUserRepository : UserRepository{

    private val users=listOf<User>(User("7872742607", "tarun.samanta@medly.com","tarun.samanta@medly.com"))
    override fun findByEmail(email: String?): User {

        val user=users.filter { it.email==email}

        if(email=="tarun.samanta@medly.com")
            return user.first()
        throw NotFoundException()



    }

}
