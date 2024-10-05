using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace EcotourismWebsite.Models
{
    public class TripReservation
    {
        [Key]
        public int TripReservationId { get; set; }
        public DateTime ReservationDate { get; set; }
        public DateTime TripDate { get; set; }

        public virtual Trip Trip { get; set; }
        [ForeignKey("Trip")]
        public int TripId { get; set; }

        public virtual ApplicationUser User { get; set; }
        [ForeignKey("ApplicationUser")]
        public int UserId { get; set; }
    }
}
