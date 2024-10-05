using EcotourismWebsite.Data;
using EcotourismWebsite.Interfaces;
using EcotourismWebsite.Models;

namespace EcotourismWebsite.Repository
{
    public class CommentRepository : Repository<Comment>, ICommentRepository
    {
        public CommentRepository(ApplicationDbContext context) : base(context)
        {

        }
        
    }
}
