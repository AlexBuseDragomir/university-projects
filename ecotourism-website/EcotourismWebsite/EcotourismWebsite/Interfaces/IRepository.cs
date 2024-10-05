using System;
using System.Collections.Generic;

namespace EcotourismWebsite.Interfaces
{
    public interface IRepository<T> where T : class
    {
        int Count(Func<T, bool> predicate);

        void Create(T entity);

        void Delete(T entity);

        IEnumerable<T> Find(Func<T, bool> predicate);

        IEnumerable<T> GetAll();

        T GetById(int id);

        void Update(T entity);

        void Save();
    }
}
