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

            string password = "P@$$w0rd";

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

            if (await userManager.FindByNameAsync("alex@yahoo.com") == null)
            {
                var user = new ApplicationUser
                {
                    UserName = "alex@yahoo.com",
                    Email = "alex@yahoo.com",
                    FirstName = "Alexandru",
                    LastName = "Dragomir",
                    Street = "Aleea Teilor",
                    City = "Craiova",
                    Province = "DJ",
                    PostalCode = "200291",
                    Country = "Romania",
                    PhoneNumber = "0772243021"
                };

                var result = await userManager.CreateAsync(user);

                if (result.Succeeded)
                {
                    await userManager.AddPasswordAsync(user, password);
                    await userManager.AddToRoleAsync(user, role1);
                }

                //adminId1 = user.Id;
            }

            if (await userManager.FindByNameAsync("andrei@gmail.com") == null)
            {
                var user = new ApplicationUser
                {
                    UserName = "andrei@gmail.com",
                    Email = "andrei@gmail.com",
                    FirstName = "Andrei",
                    LastName = "Ciurez",
                    Street = "Bulevardul Dacia",
                    City = "Craiova",
                    Province = "DJ",
                    PostalCode = "210299",
                    Country = "Romania",
                    PhoneNumber = "0748240355"
                };

                var result = await userManager.CreateAsync(user);

                if (result.Succeeded)
                {
                    await userManager.AddPasswordAsync(user, password);
                    await userManager.AddToRoleAsync(user, role2);
                }
            }

            if (await userManager.FindByNameAsync("maria@gmail.com") == null)
            {
                var user = new ApplicationUser
                {
                    UserName = "maria@gmail.com",
                    Email = "maria@gmail.com",
                    FirstName = "Maria",
                    LastName = "Stan",
                    Street = "Strada Carol I",
                    City = "Craiova",
                    Province = "DJ",
                    PostalCode = "277101",
                    Country = "Romania",
                    PhoneNumber = "0751265784"
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
