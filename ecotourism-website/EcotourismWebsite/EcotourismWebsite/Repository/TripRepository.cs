using EcotourismWebsite.Data;
using EcotourismWebsite.Interfaces;
using EcotourismWebsite.Models;

namespace EcotourismWebsite.Repository
{
    public class TripRepository : Repository<Trip>, ITripRepository
    {
        public TripRepository(ApplicationDbContext context) : base(context)
        {

        }
    }
}
