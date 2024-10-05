using EcotourismWebsite.Models;
using Microsoft.AspNetCore.Identity;
using System;
using System.Threading.Tasks;

namespace EcotourismWebsite.Data
{
    public class DummyData
    {
        public static async Task Initialize(ApplicationDbContext context,
                                            UserManager<ApplicationUser> userManager,
                                            RoleManager<ApplicationRole> roleManager)
        {
            context.Database.EnsureCreated();

            string role1 = "Administrator";
            string description1 = "This is the administrator role";

            string role2 = "NormalUser";
            string description2 = "This is the normal user role";

            string role3 = "TripGuide";
            string description3 = "This is the moderator role";

            string password = "MockPassword123";

            if (await roleManager.FindByNameAsync(role1) == null)
            {
                await roleManager.CreateAsync(new ApplicationRole(role1, description1, DateTime.Now));
            }

            if (await roleManager.FindByNameAsync(role2) == null)
            {
                await roleManager.CreateAsync(new ApplicationRole(role2, description2, DateTime.Now));
            }

            if (await roleManager.FindByNameAsync(role3) == null)
            {
                await roleManager.CreateAsync(new ApplicationRole(role3, description3, DateTime.Now));
            }

            if (await userManager.FindByNameAsync("user1@example.com") == null)
            {
                var user = new ApplicationUser
                {
                    UserName = "user1@example.com",
                    Email = "user1@example.com",
                    FirstName = "John",
                    LastName = "Doe",
                    Street = "Mock Street 1",
                    City = "Mock City",
                    Province = "XY",
                    PostalCode = "12345",
                    Country = "Mockland",
                    PhoneNumber = "0001112223"
                };

                var result = await userManager.CreateAsync(user);

                if (result.Succeeded)
                {
                    await userManager.AddPasswordAsync(user, password);
                    await userManager.AddToRoleAsync(user, role1);
                }
            }

            if (await userManager.FindByNameAsync("user2@example.com") == null)
            {
                var user = new ApplicationUser
                {
                    UserName = "user2@example.com",
                    Email = "user2@example.com",
                    FirstName = "Jane",
                    LastName = "Smith",
                    Street = "Mock Boulevard",
                    City = "Mock City",
                    Province = "XY",
                    PostalCode = "67890",
                    Country = "Mockland",
                    PhoneNumber = "0001112224"
                };

                var result = await userManager.CreateAsync(user);

                if (result.Succeeded)
                {
                    await userManager.AddPasswordAsync(user, password);
                    await userManager.AddToRoleAsync(user, role2);
                }
            }

            if (await userManager.FindByNameAsync("user3@example.com") == null)
            {
                var user = new ApplicationUser
                {
                    UserName = "user3@example.com",
                    Email = "user3@example.com",
                    FirstName = "Alice",
                    LastName = "Johnson",
                    Street = "Mock Avenue",
                    City = "Mock City",
                    Province = "XY",
                    PostalCode = "54321",
                    Country = "Mockland",
                    PhoneNumber = "0001112225"
                };

                var result = await userManager.CreateAsync(user);

                if (result.Succeeded)
                {
                    await userManager.AddPasswordAsync(user, password);
                    await userManager.AddToRoleAsync(user, role3);
                }
            }
        }
    }
}