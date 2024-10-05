using System;
using System.Collections.Generic;
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
    public class TripCategoriesController : Controller
    {
        private readonly ApplicationDbContext _context;

        public TripCategoriesController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: TripCategories
        [Authorize(Roles = "Administrator, NormalUser, TripGuide")]
        public async Task<IActionResult> Index()
        {
            return View(await _context.TripCategory.ToListAsync());
        }

        // GET: TripCategories/Details/5
        [Authorize(Roles = "Administrator, NormalUser, TripGuide")]
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tripCategory = await _context.TripCategory
                .FirstOrDefaultAsync(m => m.TripCategoryId == id);
            if (tripCategory == null)
            {
                return NotFound();
            }

            return View(tripCategory);
        }

        // GET: TripCategories/Create
        [Authorize(Roles = "Administrator, TripGuide")]
        public IActionResult Create()
        {
            return View();
        }

        // POST: TripCategories/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator, TripGuide")]
        public async Task<IActionResult> Create([Bind("TripCategoryId,TripCategoryName")] TripCategory tripCategory)
        {
            if (ModelState.IsValid)
            {
                _context.Add(tripCategory);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(tripCategory);
        }

        // GET: TripCategories/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tripCategory = await _context.TripCategory.FindAsync(id);
            if (tripCategory == null)
            {
                return NotFound();
            }
            return View(tripCategory);
        }

        // POST: TripCategories/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator, TripGuide")]
        public async Task<IActionResult> Edit(int id, [Bind("TripCategoryId,TripCategoryName")] TripCategory tripCategory)
        {
            if (id != tripCategory.TripCategoryId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(tripCategory);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!TripCategoryExists(tripCategory.TripCategoryId))
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
            return View(tripCategory);
        }

        // GET: TripCategories/Delete/5
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tripCategory = await _context.TripCategory
                .FirstOrDefaultAsync(m => m.TripCategoryId == id);
            if (tripCategory == null)
            {
                return NotFound();
            }

            return View(tripCategory);
        }

        // POST: TripCategories/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var tripCategory = await _context.TripCategory.FindAsync(id);
            _context.TripCategory.Remove(tripCategory);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool TripCategoryExists(int id)
        {
            return _context.TripCategory.Any(e => e.TripCategoryId == id);
        }
    }
}
