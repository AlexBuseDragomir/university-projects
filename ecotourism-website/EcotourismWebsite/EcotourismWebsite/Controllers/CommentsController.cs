﻿using System;
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
    public class CommentsController : Controller
    {
        private readonly ApplicationDbContext _context;

        public CommentsController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: Comments
        [Authorize(Roles = "Administrator, NormalUser, TripGuide")]
        public async Task<IActionResult> Index()
        {
            var applicationDbContext = _context.Comment.Include(c => c.Trip);
            return View(await applicationDbContext.ToListAsync());
        }

        // GET: Comments/Details/5
        [Authorize(Roles = "Administrator, NormalUser, TripGuide")]
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var comment = await _context.Comment
                .Include(c => c.Trip)
                .FirstOrDefaultAsync(m => m.CommentId == id);
            if (comment == null)
            {
                return NotFound();
            }

            return View(comment);
        }

        // GET: Comments/Create
        [Authorize(Roles = "Administrator, NormalUser, TripGuide")]
        public IActionResult Create()
        {
            ViewData["TripId"] = new SelectList(_context.Set<Trip>(), "TripId", "TripId");
            return View();
        }

        // POST: Comments/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator, NormalUser, TripGuide")]
        public async Task<IActionResult> Create([Bind("CommentId,Text,Date,PostDate,UserId,TripId,TripGuideId")] Comment comment)
        {
            if (ModelState.IsValid)
            {
                _context.Add(comment);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["TripId"] = new SelectList(_context.Set<Trip>(), "TripId", "TripId", comment.TripId);
            return View(comment);
        }

        // GET: Comments/Edit/5
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var comment = await _context.Comment.FindAsync(id);
            if (comment == null)
            {
                return NotFound();
            }
            ViewData["TripId"] = new SelectList(_context.Set<Trip>(), "TripId", "TripId", comment.TripId);
            return View(comment);
        }

        // POST: Comments/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> Edit(int id, [Bind("CommentId,Text,Date,PostDate,UserId,TripId,TripGuideId")] Comment comment)
        {
            if (id != comment.CommentId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(comment);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CommentExists(comment.CommentId))
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
            ViewData["TripId"] = new SelectList(_context.Set<Trip>(), "TripId", "TripId", comment.TripId);
            return View(comment);
        }

        // GET: Comments/Delete/5
        [Authorize(Roles = "Administrator")]
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var comment = await _context.Comment
                .Include(c => c.Trip)
                .FirstOrDefaultAsync(m => m.CommentId == id);
            if (comment == null)
            {
                return NotFound();
            }

            return View(comment);
        }

        // POST: Comments/Delete/5
        [Authorize(Roles = "Administrator")]
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var comment = await _context.Comment.FindAsync(id);
            _context.Comment.Remove(comment);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CommentExists(int id)
        {
            return _context.Comment.Any(e => e.CommentId == id);
        }
    }
}
