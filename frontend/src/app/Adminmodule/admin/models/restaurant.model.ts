export interface Restaurant {
    restaurantId: number;
    restaurantName: string;
    location: string;
    restaurantEmail: string;
    restaurantPassword?: string; // Optional: only if needed internally
  }
  