using EcotourismWebsite.Data;
using EcotourismWebsite.Interfaces;
using EcotourismWebsite.Models;

namespace EcotourismWebsite.Repository
{
    public class ApplicationUserRepository : Repository<ApplicationUser>, IApplicationUserRepository
    {
        public ApplicationUserRepository(ApplicationDbContext context) : base(context)
        {

        }
    }
}
