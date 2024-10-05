using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace EcotourismWebsite.Models
{
    public class Comment
    {
        [Key]
        public int CommentId { get; set; }
        [Column(TypeName = "nvarchar(500)")]
        public string Text { get; set; }
        public DateTime Date { get; set; }
        public DateTime PostDate { get; set; }

        public virtual ApplicationUser User { get; set; }
        [ForeignKey("ApplicationUser")]
        public int? UserId { get; set; }

        public virtual Trip Trip { get; set; }
        [ForeignKey("Trip")]
        public int TripId { get; set; }

        public virtual ApplicationUser TripGuide { get; set; }
        [ForeignKey("ApplicationUser")]
        public int? TripGuideId { get; set; }
    }
}
