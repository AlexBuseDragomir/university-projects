using EcotourismWebsite.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace EcotourismWebsite.Data
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser, ApplicationRole, string>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }
        public DbSet<Comment> Comment { get; set; }
        public DbSet<Trip> Trip { get; set; }
        public DbSet<TripCategory> TripCategory { get; set; }
        public DbSet<TripReservation> TripReservation { get; set; }
        public DbSet<ApplicationRole> ApplicationRole { get; set; }
        public DbSet<ApplicationUser> ApplicationUsers { get; set; }
    }
}
