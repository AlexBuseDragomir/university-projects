using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using EcotourismWebsite.Data;
using EcotourismWebsite.Models;
using Microsoft.AspNetCore.Authorization;

namespace EcotourismWebsite.Controllers
{
    public class TripsController : Controller
    {
        private readonly ApplicationDbContext _context;

        public TripsController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: Trips
        [Authorize(Roles = "Administrator, NormalUser, TripGuide")]
        public async Task<IActionResult> Index()
        {
            var applicationDbContext = _context.Trip.Include(t => t.TripCategory);
            return View(await applicationDbContext.ToListAsync());
        }

        // GET: Trips/Details/5
        [Authorize(Roles = "Administrator, NormalUser, TripGuide")]
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var trip = await _context.Trip
                .Include(t => t.TripCategory)
                .FirstOrDefaultAsync(m => m.TripId == id);
            if (trip == null)
            {
                return NotFound();
            }

            return View(trip);
        }

        // GET: Trips/Create
        [Authorize(Roles = "Administrator, TripGuide")]
        public IActionResult Create()
        {
            ViewData["TripCategoryId"] = new SelectList(_context.Set<TripCategory>(), "TripCategoryId", "TripCategoryId");
            return View();
        }

        // POST: Trips/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator, TripGuide")]
        public async Task<IActionResult> Create([Bind("TripId,Title,TotalPrice,Location,DateAdded,Description,TripGuideId,TripCategoryId")] Trip trip)
        {
            if (ModelState.IsValid)
            {
                _context.Add(trip);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["TripCategoryId"] = new SelectList(_context.Set<TripCategory>(), "TripCategoryId", "TripCategoryId", trip.TripCategoryId);
            return View(trip);
        }

        // GET: Trips/Edit/5
        [Authorize(Roles = "Administrator, TripGuide")]
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var trip = await _context.Trip.FindAsync(id);
            if (trip == null)
            {
                return NotFound();
            }
            ViewData["TripCategoryId"] = new SelectList(_context.Set<TripCategory>(), "TripCategoryId", "TripCategoryId", trip.TripCategoryId);
            return View(trip);
        }

        // POST: Trips/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator, TripGuide")]
        public async Task<IActionResult> Edit(int id, [Bind("TripId,Title,TotalPrice,Location,DateAdded,Description,TripGuideId,TripCategoryId")] Trip trip)
        {
            if (id != trip.TripId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(trip);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!TripExists(trip.TripId))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["TripCategoryId"] = new SelectList(_context.Set<TripCategory>(), "TripCategoryId", "TripCategoryId", trip.TripCategoryId);
            return View(trip);
        }

        // GET: Trips/Delete/5
        [Authorize(Roles = "Administrator, TripGuide")]
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var trip = await _context.Trip
                .Include(t => t.TripCategory)
                .FirstOrDefaultAsync(m => m.TripId == id);
            if (trip == null)
            {
                return NotFound();
            }

            return View(trip);
        }

        // POST: Trips/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator, TripGuide")]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var trip = await _context.Trip.FindAsync(id);
            _context.Trip.Remove(trip);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool TripExists(int id)
        {
            return _context.Trip.Any(e => e.TripId == id);
        }
    }
}
