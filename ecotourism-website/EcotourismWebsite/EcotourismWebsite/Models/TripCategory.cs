using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace EcotourismWebsite.Models
{
    public class TripCategory
    {
        [Key]
        public int TripCategoryId { get; set; }
        public int TripCategoryName { get; set; }

        public virtual IList<Trip> Trips { get; set; }
    }
}
