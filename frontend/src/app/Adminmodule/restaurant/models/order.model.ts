export interface Order {

        orderId: number;
        orderStatus: string; // e.g., "Pending", "Accepted", "Preparing", "Delivered"
        totalAmount: number;
        orderAcceptedTime?: string; // ISO 8601 format (e.g., "2025-05-28T12:34:56")      
      
}
