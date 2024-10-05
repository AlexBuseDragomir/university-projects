using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace EcotourismWebsite.Models
{
    public class Trip
    {
        [Key]
        public int TripId { get; set; }
        [Column(TypeName = "nvarchar(100)")]
        public string Title { get; set; }
        public int TotalPrice { get; set; }
        [Column(TypeName = "nvarchar(100)")]
        public string Location { get; set; }
        public DateTime DateAdded { get; set; }
        [Column(TypeName = "nvarchar(3000)")]
        public string Description { get; set; }

        public virtual IList<Comment> Comments { get; set; }

        public virtual ApplicationUser TripGuide { get; set; }
        [ForeignKey("ApplicationUser")]
        public int TripGuideId { get; set; }

        public virtual TripCategory TripCategory { get; set; }
        [ForeignKey("TripCategory")]
        public int TripCategoryId { get; set; }
    }
}
