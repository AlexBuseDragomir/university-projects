using EcotourismWebsite.Data;
using EcotourismWebsite.Interfaces;
using EcotourismWebsite.Models;

namespace EcotourismWebsite.Repository
{
    public class TripCategoryRepository : Repository<TripCategory>, ITripCategoryRepository 
    {
        public TripCategoryRepository(ApplicationDbContext context) : base(context)
        {

        }
    }
}
