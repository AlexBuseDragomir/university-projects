using EcotourismWebsite.Data;
using EcotourismWebsite.Interfaces;
using EcotourismWebsite.Models;

namespace EcotourismWebsite.Repository
{
    public class TripReservationRepository : Repository<TripReservation>, ITripReservationRepository
    {
        public TripReservationRepository(ApplicationDbContext context) : base(context)
        {

        }
    }
}
