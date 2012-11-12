# include <stdlib.h>

struct spinlock_t {
  // your own
};

typedef struct spinlock_t spinlock_t;

void spinlock_acquire(spinlock_t *lock);


void spinlock_release(spinlock_t *lock);



