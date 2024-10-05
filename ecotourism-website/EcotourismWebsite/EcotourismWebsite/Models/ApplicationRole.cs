using Microsoft.AspNetCore.Identity;
using System;

namespace EcotourismWebsite.Models
{
    public class ApplicationRole : IdentityRole
    {
        public ApplicationRole() : base() {}

        public ApplicationRole(string roleName) : base(roleName)
        {

        }

        public ApplicationRole(string roleName, string description, DateTime creationDate) :  base(roleName)
        {
            Description = description;
            CreationDate = creationDate;
        }

        public string Description { get; set; }
        public DateTime CreationDate { get; set; }
    }
}
