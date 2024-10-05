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
    public class TripReservationsController : Controller
    {
        private readonly ApplicationDbContext _context;

        public TripReservationsController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: TripReservations
        [Authorize(Roles = "Administrator, NormalUser")]
        public async Task<IActionResult> Index()
        {
            var applicationDbContext = _context.TripReservation.Include(t => t.Trip);
            return View(await applicationDbContext.ToListAsync());
        }

        // GET: TripReservations/Details/5
        [Authorize(Roles = "Administrator, NormalUser")]
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tripReservation = await _context.TripReservation
                .Include(t => t.Trip)
                .FirstOrDefaultAsync(m => m.TripReservationId == id);
            if (tripReservation == null)
            {
                return NotFound();
            }

            return View(tripReservation);
        }

        // GET: TripReservations/Create
        [Authorize(Roles = "Administrator, NormalUser")]
        public IActionResult Create()
        {
            ViewData["TripId"] = new SelectList(_context.Trip, "TripId", "TripId");
            return View();
        }

        // POST: TripReservations/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator, NormalUser")]
        public async Task<IActionResult> Create([Bind("TripReservationId,ReservationDate,TripDate,TripId,UserId")] TripReservation tripReservation)
        {
            if (ModelState.IsValid)
            {
                _context.Add(tripReservation);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["TripId"] = new SelectList(_context.Trip, "TripId", "TripId", tripReservation.TripId);
            return View(tripReservation);
        }

        // GET: TripReservations/Edit/5
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tripReservation = await _context.TripReservation.FindAsync(id);
            if (tripReservation == null)
            {
                return NotFound();
            }
            ViewData["TripId"] = new SelectList(_context.Trip, "TripId", "TripId", tripReservation.TripId);
            return View(tripReservation);
        }

        // POST: TripReservations/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> Edit(int id, [Bind("TripReservationId,ReservationDate,TripDate,TripId,UserId")] TripReservation tripReservation)
        {
            if (id != tripReservation.TripReservationId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(tripReservation);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!TripReservationExists(tripReservation.TripReservationId))
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
            ViewData["TripId"] = new SelectList(_context.Trip, "TripId", "TripId", tripReservation.TripId);
            return View(tripReservation);
        }

        // GET: TripReservations/Delete/5
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tripReservation = await _context.TripReservation
                .Include(t => t.Trip)
                .FirstOrDefaultAsync(m => m.TripReservationId == id);
            if (tripReservation == null)
            {
                return NotFound();
            }

            return View(tripReservation);
        }

        // POST: TripReservations/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var tripReservation = await _context.TripReservation.FindAsync(id);
            _context.TripReservation.Remove(tripReservation);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool TripReservationExists(int id)
        {
            return _context.TripReservation.Any(e => e.TripReservationId == id);
        }
    }
}
