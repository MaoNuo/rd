//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a RdGen v1.11.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------
#include "Class.Generated.h"



#ifdef _MSC_VER
#pragma warning( push )
#pragma warning( disable:4250 )
#pragma warning( disable:4307 )
#pragma warning( disable:4267 )
#pragma warning( disable:4244 )
#pragma warning( disable:4100 )
#endif

namespace org.example {
// companion
// constants
// initializer
void Class::initialize()
{
}
// primary ctor
// secondary constructor
// default ctors and dtors
Class::Class()
{
    initialize();
}
// reader
Class Class::read(rd::SerializationCtx& ctx, rd::Buffer & buffer)
{
    auto _id = rd::RdId::read(buffer);
    Class res{};
    withId(res, _id);
    return res;
}
// writer
void Class::write(rd::SerializationCtx& ctx, rd::Buffer& buffer) const
{
    this->rdid.write(buffer);
}
// virtual init
void Class::init(rd::Lifetime lifetime) const
{
    rd::RdBindableBase::init(lifetime);
}
// identify
void Class::identify(const rd::Identities &identities, rd::RdId const &id) const
{
    rd::RdBindableBase::identify(identities, id);
}
// getters
// intern
// equals trait
bool Class::equals(rd::ISerializable const& object) const
{
    auto const &other = dynamic_cast<Class const&>(object);
    return this == &other;
}
// equality operators
bool operator==(const Class &lhs, const Class &rhs) {
    return &lhs == &rhs;
}
bool operator!=(const Class &lhs, const Class &rhs){
    return !(lhs == rhs);
}
// hash code trait
// type name trait
std::string Class::type_name() const
{
    return "Class";
}
// static type name trait
std::string Class::static_type_name()
{
    return "Class";
}
// polymorphic to string
std::string Class::toString() const
{
    std::string res = "Class\n";
    return res;
}
// external to string
std::string to_string(const Class & value)
{
    return value.toString();
}
}

#ifdef _MSC_VER
#pragma warning( pop )
#endif
