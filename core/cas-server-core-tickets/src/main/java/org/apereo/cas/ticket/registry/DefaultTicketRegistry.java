package org.apereo.cas.ticket.registry;

import org.apereo.cas.ticket.Ticket;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the TicketRegistry that is backed by a ConcurrentHashMap.
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */
public class DefaultTicketRegistry extends AbstractTicketRegistry {

    /**
     * A HashMap to contain the tickets.
     */
    private final Map<String, Ticket> cache;

    /**
     * Instantiates a new default ticket registry.
     */
    public DefaultTicketRegistry() {
        this.cache = new ConcurrentHashMap<>();
    }

    /**
     * Creates a new, empty registry with the specified initial capacity, load
     * factor, and concurrency level.
     *
     * @param initialCapacity  - the initial capacity. The implementation
     *                         performs internal sizing to accommodate this many elements.
     * @param loadFactor       - the load factor threshold, used to control resizing.
     *                         Resizing may be performed when the average number of elements per bin
     *                         exceeds this threshold.
     * @param concurrencyLevel - the estimated number of concurrently updating
     *                         threads. The implementation performs internal sizing to try to
     *                         accommodate this many threads.
     */
    public DefaultTicketRegistry(final int initialCapacity,
                                 final float loadFactor,
                                 final int concurrencyLevel) {
        this.cache = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }

    @Override
    public void addTicket(final Ticket ticket) {
        Assert.notNull(ticket, "ticket cannot be null");
        final Ticket encTicket = encodeTicket(ticket);
        logger.debug("Added ticket [{}] to registry.", ticket.getId());
        this.cache.put(encTicket.getId(), encTicket);
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        logger.warn("Runtime memory is used as the persistence storage for retrieving and managing tickets. "
                + "Tickets that are issued during runtime will be LOST upon container restarts. This MAY impact SSO functionality.");
    }

    @Override
    public Ticket getTicket(final String ticketId) {
        if (ticketId == null) {
            return null;
        }
        final String encTicketId = encodeTicketId(ticketId);
        if (encTicketId == null) {
            return null;
        }
        return decodeTicket(this.cache.get(encTicketId));
    }

    @Override
    public boolean deleteSingleTicket(final String ticketId) {
        if (ticketId == null) {
            return false;
        }
        final String encTicketId = encodeTicketId(ticketId);
        if (encTicketId == null) {
            return false;
        }
        return this.cache.remove(encTicketId) != null;
    }


    @Override
    public Collection<Ticket> getTickets() {
        return decodeTickets(this.cache.values());
    }

    @Override
    public void updateTicket(final Ticket ticket) {
        addTicket(ticket);
    }
}
